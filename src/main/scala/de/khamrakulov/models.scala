package de.khamrakulov

import org.mongodb.scala.bson.collection.immutable.Document
import org.mongodb.scala.bson.{BsonDocument, BsonTimestamp}

/**
  * @author Timur Khamrakulov <timur.khamrakulov@gmail.com>.
  */
object models {
  object OplogOperation extends Enumeration {
    type OplogOperation = Value

    val Insert, Update, Delete = Value
  }

  import OplogOperation._

  case class OplogEntry(ns: String, oRo2: BsonDocument, ts: BsonTimestamp, op: OplogOperation)

  def documentToOplogEntry(doc: Document): OplogEntry = {
    val entry = doc.toBsonDocument
    val ns    = entry.getString("ns").getValue
    val ts    = entry.getTimestamp("ts")
    val oRo2  = if (doc.get("o").isDefined) entry.getDocument("o")
                else entry.getDocument("o2")
    val op    = entry.getString("op").getValue match {
      case "i" => Insert
      case "u" => Update
      case "d" => Delete
    }

    OplogEntry(ns, oRo2, ts, op)
  }

  case class Shard(name: String, uri: String)
}
