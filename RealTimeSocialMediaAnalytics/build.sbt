name := "RealTimeSocialMediaAnalytics"

version := "0.1.0"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "RealTimeSocialMediaAnalytics",
    version := "0.1.0",
    libraryDependencies ++= Seq(
      // Kafka
      "org.apache.kafka" %% "kafka" % "2.8.0",
      
      // MongoDB Scala Driver
      "org.mongodb.scala" %% "mongo-scala-driver" % "4.4.2",
      
      // MongoDB Reactive Streams Driver (for Completed type)
      "org.mongodb" % "mongodb-driver-reactivestreams" % "4.7.1",
      
      // Stanford CoreNLP
      "edu.stanford.nlp" % "stanford-corenlp" % "4.5.1",
      
      // JSON4S with Jackson
      "org.json4s" %% "json4s-jackson" % "3.6.11",
      
      // sttp client3 core
      "com.softwaremill.sttp.client3" %% "core" % "3.8.3",
      
      // SLF4J for logging
      "org.slf4j" % "slf4j-api" % "1.7.30",
      
      // Apache Spark Core and SQL
      "org.apache.spark" %% "spark-core" % "3.4.1",
      "org.apache.spark" %% "spark-sql" % "3.4.1",
      
      // Apache Spark MLlib
      "org.apache.spark" %% "spark-mllib" % "3.4.1"
    ),

    // Set the main class for `sbt run`
    Compile / run / mainClass := Some("TweetSentimentAnalysis")
  )
// Required to avoid IllegalAccessError with Spark on Java 17+
ThisBuild / fork := true

ThisBuild / javaOptions += "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED"  