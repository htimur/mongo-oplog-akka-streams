package de.khamrakulov

import com.typesafe.config.Config

/**
  * @author Timur Khamrakulov <timur.khamrakulov@gmail.com>.
  */
object configs {

  object MongoConfig {
    def apply(config: Config): MongoConfig = MongoConfig(
      config.getString("uri"),
      OplogConfig(config.getString("oplog.db"), config.getString("oplog.collection"))
    )
  }

  case class OplogConfig(db: String, collection: String)

  case class MongoConfig(uri: String, oplog: OplogConfig)

}
