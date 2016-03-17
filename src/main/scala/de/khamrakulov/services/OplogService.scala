package de.khamrakulov.services

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.mongodb.CursorType
import de.khamrakulov.configs.MongoConstants
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._
import org.mongodb.scala.{FindObservable, MongoClient}

trait OplogService {

  def source(client: MongoClient): Source[Document, NotUsed]

}

object OplogService {
  def apply() = new OplogServiceImpl

  class OplogServiceImpl extends OplogService {

    import rxStreams.Implicits._

    override def source(client: MongoClient): Source[Document, NotUsed] = {
      val observable = getOplogObservable(client)

      Source.fromPublisher(observable)
    }


    private def getOplogObservable(client: MongoClient): FindObservable[Document] = {
      client.getDatabase(MongoConstants.LOCAL_DATABASE)
        .getCollection(MongoConstants.OPLOG_COLLECTION)
        .find(and(
          in(MongoConstants.OPLOG_OPERATION, "i", "d", "u"),
          exists(MongoConstants.OPLOG_FROM_MIGRATE, exists = false)))
        .cursorType(CursorType.TailableAwait)
        .noCursorTimeout(true)
    }
  }

}