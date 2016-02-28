package de.khamrakulov.services

import org.mongodb.scala.MongoClient
import org.mongodb.scala.bson.collection.immutable.Document
import org.reactivestreams.Publisher

trait OplogService {

  def getCursor(): Publisher[Document]

}

object OplogService {
  def apply(client: MongoClient) = new OplogServiceImpl(client)

  private class OplogServiceImpl(private val client: MongoClient) extends OplogService {
    override def getCursor(): Publisher[Document] = ???
  }

}