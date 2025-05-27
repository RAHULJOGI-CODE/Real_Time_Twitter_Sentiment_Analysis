# Real_Time_Twitter_Sentiment_Analysis



🚀 Real-Time Twitter Sentiment Analysis Using Kafka, Spark, Scala, MongoDB 

This Big Data project performs real-time sentiment analysis on tweets using a complete data engineering and machine learning pipeline. It combines Kafka for data ingestion, Spark (Python + Scala) for stream and batch processing, Agentic AI for sentiment classification, MongoDB for storage.



🧠 Overview

This project demonstrates how to build a real-time Big Data application that:
	•	Ingests tweets via Kafka
	•	Processes them using Spark Streaming
	•	Classifies sentiment using an LLM (Agno/Groq) or MLlib
	•	Stores results in MongoDB
	•	Aggregates trends using Scala + Spark

📡 It also integrates Scala for batch analytics and Hadoop (HDFS) to store summaries, fulfilling distributed system requirements.



📐 Project Architecture

[Twitter API / CSV Simulator]
         ↓
     Kafka Producer
         ↓
    [Kafka Topic]
         ↓
  Kafka Consumer (Python)
         ↓
  Agentic AI (Agno + Groq)
         ↓
       MongoDB
     ↙         
Scala Spark  
(HDFS Output)



🔧 Technologies Used

Layer	Tech Stack
Ingestion	Apache Kafka, Docker
Real-Time Compute	PySpark (Streaming), Agentic AI (Agno)
Batch Analytics	Scala + Spark + HDFS
Model Training	MLlib (Logistic Regression on PySpark)
Storage	MongoDB
Deployment	Docker (Kafka Zookeeper setup)




🗃 Dataset Used
	•	📁 twitter_training.csv: 74,682 labeled tweets for training
	•	📁 twitter_validation.csv: 998 tweets for validation
	•	Source: Twitter Entity Sentiment Analysis - Kaggle



📦 Repository Structure

.
├── Kafka-PySpark/
│   ├── kafka_producer.py        # Sends tweets to Kafka topic
│   ├── kafka_consumer.py        # Consumes, classifies, and stores to MongoDB
├── ML PySpark Model/
│   ├── model_training.ipynb     # PySpark MLlib logistic regression
│   ├── twitter_training.csv
│   ├── twitter_validation.csv
├── Scala-Spark-Batch/
│   └── sentiment_summary.scala  # Scala job to aggregate MongoDB → HDFS
├── zk-single-kafka-single.yml   # Kafka + Zookeeper Docker setup
├── run_kafka_pipeline.py        # Launches producer + consumer together
└── bigdataproject_rapport.pdf   # French summary report (if required)




⚙️ Setup & Run

1. 🚀 Start Kafka (Docker)

docker-compose -f zk-single-kafka-single.yml up -d

2. 🧠 Train PySpark MLlib Model (optional)

Run model_training.ipynb to train and save your model.

3. 🛰️ Start Producer + Consumer

python3 run_kafka_pipeline.py

4. 📦 Run Scala Spark Batch (optional)

spark-submit --class SentimentSummary --master local Scala-Spark-Batch/sentiment_summary.scala




📈 Features
	•	✅ Live tweet ingestion using Kafka
	•	✅ Real-time sentiment classification with LLM
	•	✅ MongoDB storage of tweets and sentiments
	•	✅ Scala-based trend aggregation saved to HDFS
	•	✅ End-to-end Big Data pipeline



📌 Contribution Ideas
	•	Add toxicity or emotion classification
	•	Train your own ML model with PySpark or Scala
	•	Integrate with Elasticsearch for tweet search
	•	Add REST API endpoints for dashboard data



