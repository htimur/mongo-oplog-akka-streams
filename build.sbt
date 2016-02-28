name := "MongoDB oplog tailing with Akks Streams"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.2",
  "com.typesafe.akka" %% "akka-stream" % "2.4.2"
)
