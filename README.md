# 📰 Real-Time News Sentiment Analysis (Microservices + Kubernetes)

## 📘 Overview

This project is a **real-time sentiment analysis system** built using a **microservices architecture** with **Spring Boot**, **Kafka**, and **Kubernetes**.

The system continuously fetches news articles from an external API, streams them through Kafka, analyzes their sentiment using NLP, and exposes real-time results via reactive REST APIs.

The project demonstrates how to combine:

* **Event-driven architecture (Kafka)**
* **Reactive programming (Spring WebFlux)**
* **Microservices design**
* **Containerization (Docker)**
* **Orchestration (Kubernetes)**

---

## 🏗️ Architecture

```
Client
  │
  ▼
News Ingestion Service
  │
  ▼
Kafka (topic: news.sentences)
  │
  ▼
Sentiment Analysis Service
  │
  ▼
Real-time Sentiment Results
```

### 🔹 Services

#### 🟢 News Ingestion Service

* Fetches news articles using a keyword
* Streams articles to Kafka

#### 🔵 Sentiment Analysis Service

* Consumes messages from Kafka
* Groups messages into time windows
* Computes average sentiment
* Streams results in real-time

#### 🟡 Kafka

* Acts as a message broker between services
* Enables asynchronous, decoupled communication

#### 🟠 Zookeeper

* Manages Kafka broker coordination

---

## 🚀 Features

* 🔄 Real-time streaming of news data
* 💬 Sentiment analysis using Stanford CoreNLP
* ⚡ Reactive processing with Spring WebFlux
* 📡 Kafka-based event-driven pipeline
* ⏱️ Time-windowed sentiment aggregation
* 🐳 Dockerized microservices
* ☸️ Fully deployed on Kubernetes

---

## 🧠 Technologies Used

| Technology                | Purpose                 |
| ------------------------- | ----------------------- |
| **Java 17+**              | Programming language    |
| **Spring Boot (WebFlux)** | Reactive backend        |
| **Reactor Kafka**         | Kafka integration       |
| **Apache Kafka**          | Message streaming       |
| **Zookeeper**             | Kafka coordination      |
| **Stanford CoreNLP**      | Sentiment analysis      |
| **Docker**                | Containerization        |
| **Kubernetes**            | Container orchestration |
| **Maven**                 | Build tool              |
| **News API**              | External data source    |

---

## 🐳 Running with Docker (Local)

### Build images

```bash
docker build -t news-ingestion ./news-ingestion-service
docker build -t sentiment-analysis ./sentiment-analysis-service
```

---

## ☸️ Running with Kubernetes

### 1. Enable Kubernetes (Docker Desktop)

Create a local cluster (e.g. using **kind** or **kubeadm**).

### 2. Apply all resources

```bash
kubectl apply -f k8s/
```

### 3. Verify pods

```bash
kubectl get pods
```

### 4. Verify services

```bash
kubectl get svc
```

---

## 🌐 API Endpoints

### News Ingestion

```http
GET /api/news/ingest?keyword=technology
GET /api/news/stop-ingest
```

### Sentiment Analysis

```http
GET /api/sentiment?timeWindowSec=3
GET /api/stop-sentiment
```

---

## ⚙️ How It Works

1. User triggers ingestion with a keyword
2. News service fetches articles from News API
3. Articles are streamed into Kafka topic
4. Sentiment service consumes messages
5. Messages are grouped into time windows
6. Sentiment is calculated and streamed back

---

## 🧪 Example Output

```
7 messages, sentiment = 2.42
12 messages, sentiment = 3.1
```

---

## 💡 Key Takeaways

* Built a **decoupled, event-driven system**
* Gained hands-on experience with:

    * Kafka streaming
    * Reactive programming
    * Kubernetes deployments
* Learned how to debug real production-like issues (Kafka crash, networking)



