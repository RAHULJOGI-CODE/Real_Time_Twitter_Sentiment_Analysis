import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._
import org.apache.spark.ml.feature.RegexTokenizer
import scala.reflect.runtime.universe._
import org.apache.spark.sql.expressions.UserDefinedFunction
import scala.collection.mutable.WrappedArray
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions.udf


object TweetSentimentAnalysis {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("Tweet Sentiment Analysis")
      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    // Load dataset (CSV with columns: tweet,sentiment)
    val df = spark.read.option("header", "true").csv("/Users/rahuljogi/Desktop/@S6/Nishaaaaa/Big_Data_A/Real-Time-Social-Media-Analytic/Scala/bigdata_project_tweets_data.csv")

    // 1. Sentiment Count
    val sentimentCounts = df.groupBy("prediction").count()
    println("--- Sentiment Counts ---")
    sentimentCounts.show()

    // 2. Top Keywords per prediction
    val tokenizer = new RegexTokenizer()
      .setInputCol("tweet")
      .setOutputCol("words")
      .setPattern("\\W+")

    val tokenized = tokenizer.transform(df)

    val explodedWords = tokenized
      .select(col("prediction"), explode(col("words")).alias("word"))
      .filter(length(col("word")) > 2) // ignore short tokens like "is", "a"
      .filter(not(col("word").startsWith("http"))) // filter out URLs

    val topWords = explodedWords.groupBy("prediction", "word").count()
      .orderBy(desc("count"))

    println("--- Top Keywords per prediction ---")
    topWords.show(50, truncate = false)

    // 3. Tweet Length Analysis
    val withLength = df.withColumn("length", length(col("tweet")))
    val lengthStats = withLength.groupBy("prediction").agg(
      avg("length").alias("avg_length"),
      max("length").alias("max_length"),
      min("length").alias("min_length")
    )

    println("--- Tweet Length Stats per Sentiment ---")
    lengthStats.show()

    // 4. Hashtag Extraction & Analysis
    val hashtagRegex = "#\\w+".r
//     val extractHashtags = udf((text: String) => hashtagRegex.findAllIn(text).toSeq,
//   ArrayType(StringType))
     // Define a UDF to extract hashtags
     val extractHashtags = udf { text: String =>
  if (text != null) hashtagRegex.findAllIn(text).toSeq else Seq.empty[String]
}

    val withHashtags = df.withColumn("hashtags", extractHashtags(col("tweet")))
    val explodedTags = withHashtags.select(col("prediction"), explode(col("hashtags")).alias("hashtag"))

    val hashtagCounts = explodedTags.groupBy("hashtag").count().orderBy(desc("count"))
    val sentimentHashtagCounts = explodedTags.groupBy("prediction", "hashtag").count().orderBy(desc("count"))

    println("--- Total Hashtag Count ---")
    hashtagCounts.show(50, truncate = false)

    println("--- Hashtag Sentiment Breakdown ---")
    sentimentHashtagCounts.show(50, truncate = false)

    spark.stop()
  }
}
