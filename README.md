# microservices-project
The application simulates an e-commerce platform

# 📝 Microservices Project with Spring Boot, Kafka, Eureka, and Monitoring

A production-ready microservices architecture built using Spring Boot and Spring Cloud. This project simulates an e-commerce system and demonstrates modular design, asynchronous communication, observability, and robust DevOps practices.

> Live Blog: [Read on Medium](https://medium.com/@your-blog-link)  
> Author: Sachin Kokkarachedu
> LinkedIn](https://www.linkedin.com/in/sachinkokkarachedu/)


## Project Overview

Product Service - Manages product catalog
Order Service - Handles order placement
Inventory Service - Checks stock availability
Notification Service - Sends async notifications on order events
API Gateway - Single entry point for external clients
Discovery Server - Eureka registry for service discovery


## Tech Stack

- Language: Java 17
- Framework: Spring Boot 3.4.3
- Service Discovery: Eureka Server
- Gateway: Spring Cloud Gateway
- Database: MySQL (Product, Order & Inventory)
- Messaging: Apache Kafka
- Security: Spring Security + OAuth2 (JWT)
- Monitoring: Prometheus, Grafana, Zipkin
- Containerization: Docker


## 🛠Getting Started

### Prerequisites
- Docker & Docker Compose
- Java 17
- Maven

### Run All Services with Docker Compose
bash
docker-compose up --build


### Manually Run Each Service
bash
cd discovery-server && mvn spring-boot:run
cd product-service && mvn spring-boot:run


### Access URLs
- API Gateway: [http://localhost:8080](http://localhost:8080)
- Eureka Dashboard: [http://localhost:8761](http://localhost:8761)
- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3000](http://localhost:3000)
- Zipkin: [http://localhost:9411](http://localhost:9411)

## Service Details

### Product Service
- REST API to manage products
- MongoDB database
- Exposed via Gateway at `/api/product/**`

### Order Service
- Places order, checks inventory
- Publishes events to Kafka topic `order-placed-topic`

### Inventory Service
- Validates availability of SKU
- Used synchronously by Order Service

### Notification Service
- Kafka consumer that listens for order events

### API Gateway
- Secures endpoints with JWT
- Routes to all services via service discovery

### Discovery Server
- Hosts service registry on `http://localhost:8761`

### Monitoring
- Zipkin integrated in all services
- Prometheus scrapes Actuator metrics
- Grafana dashboards with panels for health, latency, throughput

## Build & Test

## Unit Tests
bash
mvn test

## Integration Tests (Optional)
- Testcontainers configured for MySQL & Kafka
- 
## Contribution

Contributions are welcome! Please fork and submit a PR.

This project highlights skills in:
- Distributed systems
- Spring Cloud ecosystem
- Kafka-based messaging
- Monitoring & observability
- Real-world DevOps with Docker
