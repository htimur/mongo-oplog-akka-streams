package de.khamrakulov.main.replicaSet

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import de.khamrakulov.configs.MongoConfig
import de.khamrakulov.models
import de.khamrakulov.services.OplogService
import org.mongodb.scala.MongoClient

/**
  * @author Timur Khamrakulov <timur.khamrakulov@gmail.com>.
  */
object Main extends App {

  val config = ConfigFactory.load()

  val mongoConfig = MongoConfig(config.getConfig("mongodb-repset"))

  val mongo = MongoClient(mongoConfig.uri)
  val oplog = OplogService()

  implicit val system = ActorSystem("MongoOplogTailer")
  implicit val materializer = ActorMaterializer()
  oplog.source(mongo).map(models.documentToOplogEntry).runForeach(println)

}
