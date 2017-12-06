name := "MongoDB Replica Set oplog tailing with Akks Streams"

version := "1.0"

scalaVersion := "2.12.4"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.1.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.5.7",
  "com.typesafe.akka" %% "akka-stream" % "2.5.7"
)
