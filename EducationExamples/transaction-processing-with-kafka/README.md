# Transaction Processing with Kafka

A simple showcase of **Apache Kafka** for transaction processing, including **Dead Letter Topic (DLT) handling** using Spring Boot and Kotlin.

This project demonstrates:
- Kafka producer and consumer for processing `ChargeRequest` transactions
- Retryable topics and DLT handling
- Multi-partition processing and tenant-based routing

## Prerequisites

- Docker & Docker Compose installed
- Java 21+ (for local development if needed)
- Git

## Technology stack

- Java 21
- Kotlin 1.9.5
- Spring Boot 3.5.4

## Getting Started

1. **Clone the repository**

```bash
git clone https://github.com/maksymdobrynin/transaction-processing-with-kafka.git
cd transaction-processing-with-kafka
````

2. Build the application

```bash
gradlew clean build
```

2. **Start application and required services**

```bash
docker-compose up -d
```

* This will start:

    * Kafka broker (including KRaft)
    * Kafka UI (management console) 
    * Showcase application

3. **Verify Kafka is running**

Open your browser, and go to http://localhost:8090/.
A management console should open. Check topics and consumers have been registered.

5. **Test transaction messages**

   1. Open topics in the management console 
   2. Find `inbound-transactions` topic and open it
   3. Push a message (see template below) to any available partition of `inbound-transactions` topic
   4. Check the application logs (inbound message should be indicated) and a particular partition (put message should be persisted)

```json
{
  "amount": 1090.50,
  "currency": "USD",
  "customer": "Henry Ford",
  "order": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
  "receiptEmail": "test@email.com",
  "metadata": {
    "exampleKey1": "exampleValue1",
    "exampleKey2": 12345
  }
}
```

## Notes

* Retryable topics and DLT are automatically created based on the configuration in Spring Boot
* Logs will show processed transactions and any messages forwarded to the DLT