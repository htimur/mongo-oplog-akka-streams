package de.khamrakulov.main.shardedCluster

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializerSettings, Supervision, ActorMaterializer}
import akka.stream.scaladsl.{Merge, Source}
import com.mongodb.MongoException
import com.typesafe.config.ConfigFactory
import de.khamrakulov.configs.MongoConfig
import de.khamrakulov.models
import de.khamrakulov.services.{OplogService, ShardService}
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.Document

/**
  * @author Timur Khamrakulov <timur.khamrakulov@gmail.com>.
  */
object Main extends App {

  val config = ConfigFactory.load()

  val mongoConfig = MongoConfig(config.getConfig("mongodb-sharded-cluster"))

  val mongo = MongoClient(mongoConfig.uri)
  val shardService = ShardService(mongo)
  val oplog = OplogService()

  implicit val system = ActorSystem("MongoOplogTailer")

  implicit val materializer = ActorMaterializer()

  val shardOplogSources = shardService.getShards.map({ shard =>
    val client = MongoClient(shard.uri)
    oplog.source(client)
  })

  val allShards: Source[Document, NotUsed] = shardOplogSources.foldLeft(Source.empty[Document]) {
    (prev, current) => Source.combine(prev, current)(Merge(_))
  }

  allShards.map(models.documentToOplogEntry).runForeach(println)

}
