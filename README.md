# 📧 Notification Service

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.1-brightgreen?style=for-the-badge)
![Gradle](https://img.shields.io/badge/Gradle-Build-blue?style=for-the-badge)
![Apache Kafka](https://img.shields.io/badge/Apache-Kafka-black?style=for-the-badge)
![MySQL](https://img.shields.io/badge/MySQL-Database-blue?style=for-the-badge)
![Kafka Consumer](https://img.shields.io/badge/Kafka-Consumer-success?style=for-the-badge)
![Email Notification](https://img.shields.io/badge/Email-Notification-orange?style=for-the-badge)
![DLT](https://img.shields.io/badge/Dead%20Letter-Topic-red?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Active-success?style=for-the-badge)

</div>

---

# 📖 Overview

The **Notification Service** is an enterprise-grade Kafka Consumer Microservice responsible for processing product-related events and generating notifications for downstream communication.

The service consumes events published by the Product Service, validates them, processes notification requests, prevents duplicate processing, and ensures reliable event handling using enterprise messaging patterns.

The Notification Service demonstrates production-ready Kafka consumer concepts including:

- Apache Kafka Consumer
- Consumer Groups
- Retry Mechanism
- Dead Letter Topic (DLT)
- Idempotent Consumer Pattern
- Processed Event Tracking
- Email Notification Processing
- Centralized Exception Handling

---

# 🎯 Business Problem

Modern enterprise applications require multiple downstream systems to react whenever business data changes.

Whenever a product is:

- Created
- Updated
- Deleted

different stakeholders need to be notified.

Examples include:

- Customers
- Administrators
- Warehouse Teams
- Business Users
- Audit Systems

If every service directly invokes the Notification Service through REST APIs, the system becomes tightly coupled.

Problems include:

- High dependency
- Reduced scalability
- Single point of failure
- Poor fault tolerance
- Difficult deployments

---

# 💡 Business Solution

Instead of synchronous REST communication, the Product Service publishes business events to Apache Kafka.

The Notification Service subscribes to these events asynchronously.

For every valid event, the service:

- Validates the message
- Prevents duplicate processing
- Generates notification content
- Sends email notifications
- Records processed events
- Handles failures using Retry and DLT

This architecture provides:

- Loose Coupling
- High Availability
- Fault Tolerance
- Reliable Message Processing
- Independent Deployment
- Event Replay Capability

---

# 🏢 Enterprise Concepts Demonstrated

This project demonstrates several enterprise backend engineering concepts including:

- Event-Driven Architecture
- Apache Kafka Consumer
- Consumer Groups
- Email Notification Processing
- Idempotent Consumer Pattern
- Retry Mechanism
- Dead Letter Topic (DLT)
- Kafka Error Handling
- Processed Event Tracking
- Spring Data JPA
- Layered Architecture
- Service Layer Pattern
- Repository Pattern
- Global Exception Handling
- Enterprise Configuration Management

---

# 🎯 Project Objectives

The primary objectives of this service are:

- Consume Product Events
- Generate Notifications
- Send Email Alerts
- Prevent Duplicate Processing
- Handle Kafka Failures Gracefully
- Demonstrate Enterprise Kafka Consumer Patterns
- Improve Fault Tolerance
- Enable Reliable Event Processing

---

# ✨ Features

## Kafka Consumer

- Product Event Consumer
- Consumer Groups
- JSON Event Deserialization
- Manual Offset Management

---

## Notification Processing

- Email Notification
- Product Event Notification
- Notification Persistence
- Notification Status Tracking

---

## Reliability

- Retry Mechanism
- Dead Letter Topic
- Kafka Error Handler
- Duplicate Event Detection
- Processed Event Tracking

---

## Exception Handling

- Retryable Exceptions
- Non-Retryable Exceptions
- Global Exception Handling
- Failure Logging

---

## Persistence

- Notification Table
- Processed Event Table
- Spring Data JPA
- MySQL Integration

---

# 🛠 Technology Stack

| Category | Technology |
|------------|------------|
| Language | Java 21 |
| Framework | Spring Boot 4.1 |
| Build Tool | Gradle |
| Database | MySQL |
| ORM | Spring Data JPA |
| Messaging | Apache Kafka |
| Serialization | Jackson |
| Validation | Jakarta Validation |
| Testing | JUnit |

---

# 🏛 High-Level Architecture

```text
                 Product Service

                        │

                Product Events

                        │

                        ▼

                 Apache Kafka

                        │

                        ▼

            Notification Service

                        │

             Kafka Consumer Listener

                        │

                        ▼

             Notification Service

             Business Processing

             ┌───────────────┐

             ▼               ▼

 Notification Table   Processed Event Table

             │

             ▼

        Email Service

             │

             ▼

     End User Notification
```

---

# 🏗 Consumer Architecture

```text
                 Kafka Topic

                      │

                      ▼

              Kafka Consumer

                      │

              Event Validation

                      │

        Duplicate Event Detection

                      │

        ┌─────────────┴─────────────┐

        ▼                           ▼

Already Processed             New Event

        │                           │

 Ignore Event          Generate Notification

                                    │

                                    ▼

                          Send Email Notification

                                    │

                                    ▼

                        Save Processed Event

                                    │

                                    ▼

                             Commit Offset
```

---
# 📂 Project Structure

```text
notification-service
│
├── gradle/
│
├── src
│   ├── main
│   │
│   ├── java
│   │   └── com
│   │       └── kafka
│   │           └── notification_service
│   │
│   │               ├── Messaging
│   │               │   ├── Consumer
│   │               │   ├── config
│   │               │   └── event
│   │               │
│   │               ├── entity
│   │               │   ├── Notification.java
│   │               │   ├── NotificationStatus.java
│   │               │   ├── ProcessedEvent.java
│   │               │   ├── ProductStatus.java
│   │               │   └── EventType.java
│   │               │
│   │               ├── Exception
│   │               │   ├── RetryableException.java
│   │               │   └── NonRetryableException.java
│   │               │
│   │               ├── repository
│   │               │   ├── NotificationRepository.java
│   │               │   └── ProcessedEventRepository.java
│   │               │
│   │               ├── service
│   │               │   ├── EmailService.java
│   │               │   ├── EmailServiceImpl.java
│   │               │   ├── NotificationService.java
│   │               │   ├── NotificationServiceImpl.java
│   │               │   ├── ProcessedEventService.java
│   │               │   └── ProcessedEventServiceImpl.java
│   │               │
│   │               └── NotificationServiceApplication.java
│   │
│   ├── resources
│   │   └── application.yml
│   │
│   └── test
│
├── build.gradle
├── .gitignore
└── README.md
```

---

# 📦 Package Responsibilities

| Package | Responsibility |
|----------|---------------|
| **Messaging.config** | Configures Apache Kafka consumers, DLT handling, error handlers, deserializers, and other messaging-related beans. |
| **Messaging.Consumer** | Consumes Product Events from Apache Kafka and forwards them to the business layer for notification processing. |
| **Messaging.event** | Contains Kafka Event Models, Event Types, and shared messaging contracts used between microservices. |
| **entity** | Contains JPA entities representing Notification records, Processed Events, Notification Status, Product Status, and related domain models. |
| **Exception** | Defines custom Retryable and Non-Retryable exceptions used by Kafka retry mechanisms and Dead Letter Topic processing. |
| **repository** | Spring Data JPA repositories responsible for Notification persistence and processed event tracking. |
| **service** | Implements business logic including notification generation, email delivery, processed event management, and notification persistence. |
| **resources** | Stores centralized Spring Boot configuration including MySQL, Kafka Consumer, Gmail SMTP, and Spring Profiles. |
| **test** | Contains unit and integration tests validating Kafka consumers, email services, repositories, and business logic. |
| **NotificationServiceApplication** | Main Spring Boot application responsible for bootstrapping the Notification Service. |

---

# 📌 Layered Architecture

```text
                  Apache Kafka
                       │
                       ▼
             Kafka Consumer Layer
                       │
                       ▼
              Notification Service
                       │
            ┌──────────┴──────────┐
            ▼                     ▼
      Email Service      Processed Event Service
            │                     │
            ▼                     ▼
 Notification Repository   Processed Event Repository
            │                     │
            └──────────┬──────────┘
                       ▼
                     MySQL
```

---

# 📚 Package Overview

The Notification Service follows a clean **Layered Architecture** combined with **Event-Driven Architecture**, where each package is responsible for a specific concern.

- **Messaging** manages Kafka communication, including consumer configuration, event models, deserialization, and message consumption.
- **Service** contains the core business logic responsible for generating notifications, sending emails, and tracking processed events.
- **EmailService** abstracts email delivery using Spring Boot Mail and Gmail SMTP.
- **Repository** provides database access through Spring Data JPA repositories.
- **Entity** represents the database schema and domain models.
- **Exception** defines retryable and non-retryable exceptions to support reliable Kafka processing and Dead Letter Topic (DLT) handling.
- **Resources** centralizes application configuration, including MySQL, Kafka, SMTP, and Spring Profiles.
- **Test** contains automated unit and integration tests to verify the correctness of notification processing.

This layered structure promotes **separation of concerns**, **maintainability**, **testability**, and **scalability**, making the service suitable for enterprise event-driven microservice architectures.

---

# ⭐ Key Highlights

✅ Apache Kafka Consumer

✅ Email Notification Processing

✅ Idempotent Consumer Pattern

✅ Retry Mechanism

✅ Dead Letter Topic (DLT)

✅ Processed Event Tracking

✅ Enterprise Layered Architecture

✅ Spring Boot 4.1

✅ MySQL Integration

✅ Production-Oriented Design

---
---

# 🏗️ Application Architecture

The Notification Service follows a **Layered Architecture** combined with an **Event-Driven Architecture**.

Instead of exposing REST endpoints for business operations, the service asynchronously consumes product events from Apache Kafka and generates email notifications.

```text
                  +-----------------------+
                  |    Product Service    |
                  +-----------+-----------+
                              |
                       Product Events
                              |
                              ▼
                    +----------------------+
                    |     Apache Kafka     |
                    +-----------+----------+
                                |
                                ▼
                    +----------------------+
                    | Kafka Consumer Layer |
                    +-----------+----------+
                                |
                                ▼
                    +----------------------+
                    |   Event Validation   |
                    +-----------+----------+
                                |
                                ▼
                    +----------------------+
                    | Duplicate Detection  |
                    +-----------+----------+
                                |
               +----------------+----------------+
               |                                 |
               ▼                                 ▼
      Already Processed                 New Event
               |                                 |
               ▼                                 ▼
        Ignore Event                 Notification Service
                                                  |
                                                  ▼
                                           Email Service
                                                  |
                                                  ▼
                                        Send Email Notification
                                                  |
                                                  ▼
                                         Save Notification
                                                  |
                                                  ▼
                                        Save Processed Event
```

---

# 📖 Layer Responsibilities

## 1️⃣ Kafka Consumer Layer

Responsible for receiving Product Events from Kafka.

Responsibilities include:

- Consume Product Events
- Deserialize JSON Payload
- Validate Events
- Delegate Processing

---

## 2️⃣ Notification Service Layer

Contains the complete business logic.

Responsibilities include:

- Build Notification Content
- Generate Email Subject
- Generate Email Body
- Save Notification
- Update Notification Status
- Handle Business Exceptions

---

## 3️⃣ Email Service

Responsible for sending email notifications.

Responsibilities

- Build MIME Email
- Configure Recipient
- Configure Subject
- Configure HTML Body
- Send Email using JavaMailSender

---

## 4️⃣ Repository Layer

Responsible for database persistence.

Repositories include

- NotificationRepository
- ProcessedEventRepository

Responsibilities

- Notification Persistence
- Duplicate Event Detection
- Processed Event Tracking

---

## 5️⃣ Entity Layer

Contains JPA entities.

### Notification

Stores notification details.

Includes

- Product Information
- Recipient Email
- Subject
- Body
- Notification Status
- Failure Reason
- Sent Timestamp

---

### ProcessedEvent

Stores processed Kafka Event IDs.

Purpose

- Duplicate Detection
- Idempotent Consumer Pattern

---

# 📧 Notification Processing Flow

```text
Kafka Product Event

        │

        ▼

Notification Consumer

        │

        ▼

Validate Event

        │

        ▼

Check Processed Event

        │

   ┌────┴────┐

   │         │

   ▼         ▼

Duplicate   New Event

   │          │

 Ignore       ▼

        Create Notification

               │

               ▼

         Save Notification

               │

               ▼

         Send Email

               │

               ▼

     Save Processed Event

               │

               ▼

         Commit Offset
```

---

# 📨 Email Processing Workflow

```text
Notification

      │

      ▼

Build Email

      │

      ▼

Recipient Email

      │

      ▼

Subject

      │

      ▼

HTML Body

      │

      ▼

JavaMailSender

      │

      ▼

SMTP Server

      │

      ▼

Recipient Inbox
```

---

# 🔄 Event Processing Lifecycle

```text
Product Created

        │

        ▼

Kafka Topic

        │

        ▼

Notification Consumer

        │

        ▼

Validate Event

        │

        ▼

Generate Notification

        │

        ▼

Persist Notification

        │

        ▼

Send Email

        │

        ▼

Mark Event Processed
```

The same lifecycle applies to:

- Product Updated
- Product Deleted

---

# 🛡️ Idempotent Consumer Pattern

Apache Kafka provides **at-least-once delivery**.

Therefore duplicate messages may occur.

Without duplicate detection

```text
Receive Event

↓

Send Email

↓

Consumer Crash

↓

Kafka Redelivers Event

↓

Duplicate Email ❌
```

---

Using ProcessedEvent

```text
Receive Event

      │

      ▼

Check Event ID

      │

┌─────┴─────┐

│           │

▼           ▼

Exists    Doesn't Exist

│            │

▼            ▼

Ignore   Send Email

              │

              ▼

 Save Processed Event
```

This guarantees email notifications are sent only once.

---

# 🔁 Retry Strategy

Temporary failures automatically trigger retries.

Examples

- SMTP Server unavailable
- Database unavailable
- Kafka timeout
- Network interruption

Workflow

```text
Consume Event

      │

      ▼

Processing Failed

      │

      ▼

Retry

      │

 ┌────┴────┐

 ▼         ▼

Success   Failed

 │          │

 ▼          ▼

Commit    Dead Letter Topic
```

---

# ☠️ Dead Letter Topic (DLT)

Messages that continue to fail after retries are redirected to a Dead Letter Topic.

Benefits

- Prevent Consumer Blocking
- Preserve Failed Messages
- Manual Investigation
- Future Replay

Workflow

```text
Kafka Event

      │

      ▼

Notification Consumer

      │

      ▼

Retry

      │

      ▼

Still Failed

      │

      ▼

Dead Letter Topic

      │

      ▼

DLT Consumer

      │

      ▼

Logging
```

---

# 🚨 Error Handling Strategy

Failures are categorized into:

## Retryable Exceptions

Examples

- SMTP Failure
- Network Failure
- Database Connection Failure
- Kafka Timeout

Automatically retried.

---

## Non-Retryable Exceptions

Examples

- Invalid Payload
- Missing Required Fields
- Unsupported Event Type

Immediately redirected to the Dead Letter Topic.

---

# 📊 Sequence Diagram

```text
Product Service

      │

Publish Event

      │

      ▼

Kafka

      │

      ▼

Notification Consumer

      │

Validate Event

      │

Check Duplicate

      │

Generate Notification

      │

Send Email

      │

Save Notification

      │

Save Processed Event

      │

Commit Offset

      │

Finished
```

---

# 🚀 Benefits of This Architecture

- Loose Coupling
- Reliable Email Delivery
- Event Replay
- Duplicate Prevention
- Retry Support
- Dead Letter Topic
- Fault Tolerance
- Consumer Scalability
- Production Readiness
- Enterprise Design

---
---

# 🗄️ Database Design

The Notification Service uses **MySQL** to persist notification records and implement the **Idempotent Consumer Pattern**.

The application maintains two primary tables:

- **notifications** – Stores notification details and delivery status.
- **processed_events** – Tracks processed Kafka events to prevent duplicate processing.

This design ensures reliable notification delivery while maintaining complete processing history.

---

# 📧 Notification Table (`notifications`)

The `notifications` table stores notification details generated from Product Events.

| Column | Type | Description |
|---------|------|-------------|
| `id` | BIGINT | Primary Key. Auto-generated notification identifier. |
| `event_id` | VARCHAR | Unique event identifier received from Product Service. |
| `correlation_id` | VARCHAR | Correlation ID used for distributed tracing. |
| `event_type` | ENUM | Product event type (Created, Updated, Deleted). |
| `product_id` | BIGINT | Product identifier. |
| `product_name` | VARCHAR | Product name. |
| `sku` | VARCHAR | Product SKU. |
| `recipient_email` | VARCHAR | Recipient email address. |
| `subject` | VARCHAR | Email subject. |
| `body` | LONGTEXT | HTML email body. |
| `message` | VARCHAR | Notification summary message. |
| `status` | ENUM | Notification status. |
| `failure_reason` | LONGTEXT | Failure reason if email delivery fails. |
| `created_at` | TIMESTAMP | Notification creation timestamp. |
| `sent_at` | TIMESTAMP | Timestamp when email was successfully sent. |

### Constraints

- Primary Key on `id`
- Event ID is mandatory
- Correlation ID is mandatory
- Event Type is mandatory
- Product ID is mandatory
- Product Name is mandatory
- SKU is mandatory
- Recipient Email is mandatory
- Subject is mandatory
- Message is mandatory
- Notification Status is mandatory

### Automatic Timestamp

The `createdAt` field is automatically populated using Hibernate's `@CreationTimestamp`.

---

# ✅ Processed Events Table (`processed_events`)

The `processed_events` table implements the **Idempotent Consumer Pattern**.

Each successfully processed Kafka event is stored so duplicate events can be safely ignored.

| Column | Type | Description |
|---------|------|-------------|
| `id` | BIGINT | Primary Key. |
| `event_id` | VARCHAR | Unique Kafka Event Identifier. |
| `processed_at` | TIMESTAMP | Time at which the event was successfully processed. |

### Constraints

- Primary Key on `id`
- Unique Constraint on `event_id`

---

# 🗂️ Database Relationship

```text
+-----------------------------------------------------------------------+
|                           NOTIFICATIONS                               |
+-----------------------------------------------------------------------+
| id (PK)                                                               |
| event_id                                                              |
| correlation_id                                                        |
| event_type                                                            |
| product_id                                                            |
| product_name                                                          |
| sku                                                                   |
| recipient_email                                                       |
| subject                                                               |
| body                                                                  |
| message                                                               |
| status                                                                |
| failure_reason                                                        |
| created_at                                                            |
| sent_at                                                               |
+-----------------------------------------------------------------------+

                           ▲
                           │
                     Generated From
                           │
                    Kafka Product Events
                           │
                           ▼

+--------------------------------------------------+
|                PROCESSED_EVENTS                  |
+--------------------------------------------------+
| id (PK)                                          |
| event_id (UNIQUE)                                |
| processed_at                                     |
+--------------------------------------------------+
```

---

# 📧 Email Configuration

The Notification Service integrates with **Gmail SMTP** using Spring Boot Mail.

| Property | Value |
|----------|-------|
| Mail Server | Gmail SMTP |
| SMTP Port | 587 |
| Authentication | Enabled |
| STARTTLS | Enabled |
| Email Format | HTML (MimeMessage) |

### Email Features

- HTML Email Support
- MIME Message
- Configurable Recipient
- Custom Subject
- Rich Email Body

---

# 📨 Kafka Consumer Configuration

The Notification Service consumes Product Events from Apache Kafka.

### Consumer Configuration

| Property | Value |
|----------|-------|
| Bootstrap Servers | `localhost:9092, localhost:9094` |
| Consumer Group | `notification-service-group` |
| Key Deserializer | `StringDeserializer` |
| Auto Offset Reset | `earliest` |
| Auto Commit | `false` |
| Max Poll Records | `10` |
| Max Poll Interval | `300000 ms` |
| Session Timeout | `10000 ms` |
| Heartbeat Interval | `3000 ms` |
| Trusted Packages | `*` |

### Consumer Reliability

The Kafka Consumer is configured for reliable event processing using:

- Manual Offset Management
- Consumer Groups
- Controlled Poll Size
- Heartbeat Monitoring
- Session Timeout
- Earliest Offset Recovery

---

# 🌍 Spring Profiles

The application supports multiple runtime environments.

| Profile | Purpose | Logging |
|---------|---------|---------|
| `local` | Local Development | INFO |
| `cut` | Component Unit Testing | INFO |
| `ete` | End-to-End Testing | INFO |
| `drt` | Development Regression Testing | INFO |
| `test` | Automated Testing | DEBUG |
| `prod` | Production | WARN |

### Test Profile

The `test` profile enables:

- `ddl-auto=create-drop`
- SQL logging
- DEBUG logging

### Production Profile

The `prod` profile is optimized for production deployments by disabling SQL logging and using WARN-level logging.

---

# 🚨 Exception Handling Strategy

The Notification Service distinguishes between temporary and permanent failures.

## Retryable Exceptions

Examples include:

- SMTP server unavailable
- Database connectivity issues
- Kafka timeout
- Network interruptions

These failures trigger automatic retry processing.

---

## Non-Retryable Exceptions

Examples include:

- Invalid event payload
- Unsupported event type
- Missing mandatory fields
- Corrupted messages

These failures are immediately redirected to the Dead Letter Topic (DLT).

---

# 📊 Logging Strategy

Logging is profile-based.

| Environment | Log Level |
|------------|-----------|
| Local | INFO |
| CUT | INFO |
| ETE | INFO |
| DRT | INFO |
| Test | DEBUG |
| Production | WARN |

---

# 📂 Configuration Flow

```text
                    application.yml
                           │
       ┌───────────────────┼────────────────────┐
       │                   │                    │
       ▼                   ▼                    ▼
 Server Config      Database Config      Kafka Consumer
       │                   │                    │
       ▼                   ▼                    ▼
 Spring Boot        Spring Data JPA      Kafka Listener
       │
       ▼
 Email Configuration
       │
       ▼
 Gmail SMTP
       │
       ▼
 Notification Service
```

---

# 📋 Configuration Highlights

- Spring Boot 4.1
- Apache Kafka Consumer
- Gmail SMTP Integration
- HTML Email Support
- JavaMailSender
- MySQL Integration
- Spring Data JPA
- Manual Offset Management
- Consumer Groups
- Idempotent Consumer Pattern
- Multiple Spring Profiles
- Environment-Specific Logging
- Production-Oriented Configuration

---
---

# 🚀 Getting Started

This section explains how to configure and run the Notification Service locally.

---

# 📋 Prerequisites

Ensure the following software is installed before running the application.

| Software | Recommended Version |
|-----------|---------------------|
| Java | 21 |
| Spring Boot | 4.1 |
| Gradle | 8+ |
| MySQL | 8.x |
| Apache Kafka | 4.x (KRaft Mode) |
| Git | Latest |
| IntelliJ IDEA | Latest |

---

# 📥 Clone Repository

```bash
git clone https://github.com/PSaiRam32/notification-management-kafka.git

cd notification-management-kafka
```

---

# 🗄️ Configure MySQL

Create the Notification database.

```sql
CREATE DATABASE kafka_notify;
```

Configure the datasource.

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/kafka_notify
    username: ${DB_USERNAME}
    password: ${DB_PASSWORD}
```

---

# 📧 Configure Gmail SMTP

The Notification Service uses Gmail SMTP to send email notifications.

```yaml
spring:
  mail:
    host: smtp.gmail.com
    port: 587
    username: ${MAIL_USERNAME}
    password: ${MAIL_PASSWORD}

    properties:
      mail.smtp.auth: true
      mail.smtp.starttls.enable: true
```

> **Note**
>
> Never commit your Gmail App Password to GitHub.
>
> Store email credentials securely using environment variables or an external configuration management solution.

---

# 📨 Start Apache Kafka (KRaft Mode)

This project uses **Apache Kafka running in KRaft Mode**.

No ZooKeeper is required.

The Notification Service connects to a two-node Kafka cluster.

```text
Broker 1

localhost:9092

Broker 2

localhost:9094
```

---

### Verify Kafka

List all topics.

```bash
kafka-topics --bootstrap-server localhost:9092 --list
```

Expected topics

```
product-events

product-events-dlt
```

---

# ▶️ Build Project

Linux / macOS

```bash
./gradlew clean build
```

Windows

```cmd
gradlew.bat clean build
```

---

# ▶️ Run Notification Service

```bash
./gradlew bootRun
```

or

```cmd
gradlew.bat bootRun
```

Application URL

```
http://localhost:8084
```

---

# 📡 Kafka Topics

The Notification Service consumes the following Kafka topics.

| Topic | Purpose |
|---------|---------|
| product-events | Receives Product Events |
| product-events-dlt | Stores permanently failed events |

---

# 👥 Consumer Group

Consumer Group

```
notification-service-group
```

Using Consumer Groups provides

- Horizontal Scaling
- Automatic Partition Assignment
- High Availability
- Fault Tolerance
- Load Balancing

---

# 📧 Email Delivery Workflow

```text
Product Service

      │

Publish Product Event

      │

      ▼

Apache Kafka

      │

      ▼

Notification Consumer

      │

Validate Event

      │

Generate Notification

      │

Persist Notification

      │

Generate HTML Email

      │

JavaMailSender

      │

SMTP Server

      │

Recipient Mailbox

      │

Save Processed Event

      │

Commit Offset
```

---

# 🧪 Testing

Recommended testing strategy.

## Unit Testing

- Notification Service
- Email Service
- Kafka Consumer Logic
- Event Validation
- Utility Classes

---

## Integration Testing

- Kafka Consumer
- Gmail SMTP Integration
- MySQL Repository
- Processed Event Tracking
- Duplicate Detection

---

## End-to-End Testing

Complete workflow.

```text
Product Service

↓

Apache Kafka

↓

Notification Service

↓

Email Sent

↓

Database Updated
```

---

# 📈 Performance Considerations

The Notification Service has been designed for scalable event processing.

Current optimizations include

- Consumer Groups
- Manual Offset Management
- Controlled Poll Size
- Idempotent Consumer Pattern
- Duplicate Detection

Future optimizations

- Batch Email Processing
- Connection Pool Optimization
- SMTP Connection Reuse
- Kafka Partition Scaling
- Horizontal Consumer Scaling

---

# 📊 Monitoring

Recommended production metrics

- Consumer Lag
- Kafka Throughput
- Email Success Rate
- Email Failure Rate
- Retry Count
- Dead Letter Topic Count
- Processing Time
- Database Latency
- JVM Metrics

Recommended tools

- Spring Boot Actuator
- Prometheus
- Grafana

---

# 🔒 Security Considerations

For production deployments consider implementing

- Spring Security
- OAuth2
- JWT Authentication
- SMTP Credential Encryption
- Secure Database Credentials
- TLS Encryption
- Secrets Management
- Role-Based Access Control (RBAC)

---

# 🏭 Production Readiness

The Notification Service demonstrates several production-ready enterprise patterns.

Implemented

- Event-Driven Architecture
- Apache Kafka Consumer
- Consumer Groups
- Manual Offset Management
- Retry Strategy
- Dead Letter Topic (DLT)
- Idempotent Consumer Pattern
- Gmail SMTP Integration
- Notification Persistence
- Processed Event Tracking
- Spring Profiles
- Global Exception Handling

Future Enhancements

- Docker
- Docker Compose
- Kubernetes
- Spring Boot Actuator
- Prometheus
- Grafana
- OpenTelemetry
- ELK Stack
- Centralized Configuration
- CI/CD Pipeline

---

# 📚 Learning Outcomes

This project demonstrates practical implementation of

- Apache Kafka Consumer
- Event-Driven Architecture
- Enterprise Messaging
- JavaMailSender
- Gmail SMTP Integration
- Consumer Groups
- Idempotent Consumer Pattern
- Retry Mechanism
- Dead Letter Topic
- Spring Boot
- Spring Data JPA
- Enterprise Layered Architecture

---

# 📌 Best Practices Followed

- Event-Driven Communication
- Loose Coupling
- Reliable Event Processing
- Duplicate Event Prevention
- Retry Handling
- Dead Letter Topic
- Clean Code Principles
- Layered Architecture
- Separation of Concerns
- Production-Oriented Configuration
- Email Service Abstraction

---
---

# 🚀 Future Roadmap

The Notification Service has been designed with extensibility in mind. Future enhancements can further improve scalability, observability, and reliability.

## Planned Features

- SMS Notification Integration
- Push Notification Support
- WhatsApp Notification Integration
- Notification Templates
- Multi-language Email Support
- Notification Preferences
- Notification Scheduling
- Bulk Notification Processing
- Notification Audit History
- Retry Dashboard

---

## Kafka Enhancements

Future Kafka improvements include:

- Retry Topics
- Dead Letter Queue Monitoring
- Kafka Schema Registry
- Message Compression
- Kafka Transactions
- Partition Scaling
- Consumer Parallelism
- Event Replay Support

---

## Cloud & DevOps

Future deployment enhancements

- Docker
- Docker Compose
- Kubernetes
- Helm Charts
- GitHub Actions
- Jenkins CI/CD
- OpenShift Deployment

---

## Monitoring & Observability

Recommended production integrations

- Spring Boot Actuator
- Prometheus
- Grafana
- OpenTelemetry
- ELK Stack
- Distributed Tracing
- Centralized Logging

---

## Security Improvements

Future security enhancements

- Spring Security
- OAuth2
- JWT Authentication
- SMTP Credential Encryption
- Secret Management
- HTTPS/TLS
- Role-Based Access Control (RBAC)
- Audit Logging

---

# 📚 Key Concepts Demonstrated

This project demonstrates several enterprise-grade backend engineering concepts.

- Apache Kafka Consumer
- Event-Driven Architecture
- Consumer Groups
- Kafka Listener
- Manual Offset Management
- Idempotent Consumer Pattern
- Dead Letter Topic (DLT)
- Retry Strategy
- Gmail SMTP Integration
- HTML Email Generation
- JavaMailSender
- Spring Data JPA
- Layered Architecture
- Clean Code Principles
- Enterprise Configuration Management

---

# 🏆 Enterprise Design Patterns Used

| Pattern | Purpose |
|----------|---------|
| Layered Architecture | Separation of concerns |
| Repository Pattern | Data access abstraction |
| Service Layer Pattern | Business logic encapsulation |
| Idempotent Consumer Pattern | Prevent duplicate message processing |
| Retry Pattern | Recover from transient failures |
| Dead Letter Topic (DLT) | Handle permanently failed events |
| Event-Driven Architecture | Asynchronous communication |

---

# 🎯 Interview Highlights

This project demonstrates practical implementation of enterprise messaging and distributed systems.

Topics covered include:

- Apache Kafka Consumer
- Event-Driven Microservices
- Consumer Groups
- Offset Management
- Manual Offset Commit
- Gmail SMTP Integration
- HTML Email Generation
- Idempotent Consumer Pattern
- Retry Mechanism
- Dead Letter Topic (DLT)
- Fault-Tolerant Event Processing
- Enterprise Layered Architecture

---

# 🤝 Contributing

Contributions are welcome.

If you would like to contribute:

1. Fork the repository.
2. Create a feature branch.
3. Implement your changes.
4. Commit your changes with meaningful commit messages.
5. Push the branch to your fork.
6. Create a Pull Request.

Please ensure new code follows the existing architecture and coding standards.

---

# 📄 License

This project is intended for educational and learning purposes.

You are welcome to study, fork, and extend the project while respecting the repository license.

---

# 👨‍💻 Author

**Sai Ram Paidipati**

Java Backend Engineer

### Connect with me

- GitHub: https://github.com/PSaiRam32
- LinkedIn: https://www.linkedin.com/in/sairam-paidipati/

---

# ⭐ Support the Project

If this project helped you understand:

- Apache Kafka
- Event-Driven Architecture
- Kafka Consumer
- Gmail SMTP Integration
- Email Notification Processing
- Retry Mechanism
- Dead Letter Topic (DLT)
- Idempotent Consumer Pattern

please consider giving this repository a ⭐ on GitHub.

Your support encourages continuous improvements and helps other developers discover the project.

---

# 📌 Project Summary

The **Notification Service** is an enterprise-grade Kafka Consumer Microservice built using **Java 21**, **Spring Boot 4.1**, **Apache Kafka**, **MySQL**, and **Spring Mail**.

It asynchronously consumes Product Events, generates rich HTML email notifications, persists notification history, prevents duplicate event processing using the **Idempotent Consumer Pattern**, and provides reliable processing through **Retry Mechanisms** and **Dead Letter Topics (DLT)**.

By combining asynchronous messaging, email delivery, duplicate prevention, and production-oriented configuration, this project demonstrates modern enterprise microservice design principles for scalable notification systems.

---

# 🏅 Why This Project?

This project was built to demonstrate real-world enterprise backend engineering concepts rather than a simple email sender.

It showcases:

- Event-driven communication using Apache Kafka
- Reliable notification processing
- Enterprise email delivery
- Duplicate event prevention
- Fault-tolerant consumer design
- Layered architecture
- Production-oriented configuration
- Clean and maintainable codebase

These concepts are widely adopted in modern distributed systems and large-scale enterprise applications.

---

# 🙏 Acknowledgements

This project was developed to explore enterprise backend development concepts and practical event-driven microservice design.

Special focus areas include:

- Apache Kafka Messaging
- Enterprise Notification Systems
- Reliable Event Processing
- Gmail SMTP Integration
- Idempotent Consumer Pattern
- Retry & Dead Letter Topic (DLT)
- Spring Boot Best Practices
- Scalable Microservice Architecture

Thank you for exploring this repository.

Feedback, suggestions, and contributions are always appreciated.

---
