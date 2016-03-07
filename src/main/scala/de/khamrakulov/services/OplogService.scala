package de.khamrakulov.services

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.mongodb.CursorType
import de.khamrakulov.configs.{MongoConstants, OplogConfig}
import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.model.Filters._

trait OplogService {

  def source: Source[Document, NotUsed]

}

object OplogService {
  def apply(client: MongoClient, oplogConfig: OplogConfig) = new OplogServiceImpl(client, oplogConfig)

  class OplogServiceImpl(client: MongoClient, oplogConfig: OplogConfig) extends OplogService {

    import rxStreams.Implicits._

    private val collection = client.getDatabase(MongoConstants.CONFIG_DATABASE)
                                   .getCollection(MongoConstants.OPLOG_COLLECTION)

    override def source: Source[Document, NotUsed] = {
      val observable = collection.find(in(MongoConstants.OPLOG_OPERATION, "i", "d", "u"))
                                 .cursorType(CursorType.TailableAwait)
                                 .noCursorTimeout(true)

      Source.fromPublisher(observable)
    }
  }

}