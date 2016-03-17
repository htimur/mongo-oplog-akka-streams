package de.khamrakulov

import com.typesafe.config.Config

/**
  * @author Timur Khamrakulov <timur.khamrakulov@gmail.com>.
  */
object configs {

  object MongoConstants {
    val CONFIG_DATABASE               = "config"
    val SHARDS_COLLECTION             = "shards"
    val SHARDS_ID                     = "_id"
    val SHARDS_HOST                   = "host"
    val LOCAL_DATABASE                = "local"
    val OPLOG_COLLECTION              = "oplog.rs"
    val OPLOG_NAMESPACE               = "ns"
    val OPLOG_TIMESTAMP               = "ts"
    val OPLOG_OPERATION               = "op"
    val OPLOG_OBJECT_LOCATOR_1        = "o"
    val OPLOG_OBJECT_LOCATOR_2        = "o2"
    val OPLOG_OBJECT_LOCATOR_ID_FIELD = "_id"
    val OPLOG_FROM_MIGRATE            = "fromMigrate"
  }

  object MongoConfig {
    def apply(config: Config): MongoConfig = MongoConfig(config.getString("uri"))
  }

  case class OplogConfig(db: String, collection: String)

  case class MongoConfig(uri: String)

}
