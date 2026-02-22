# ExpenseService

ExpenseService is a Spring Boot application responsible for managing expense records within the Expense Tracker ecosystem. It consumes expense data events from the DS_Service via Apache Kafka and persists them in a MySQL database. It also exposes a RESTful API endpoint for retrieving a user's expenses.

## Key Features

- **Spring Boot**: Utilizes Spring Boot for rapid development and auto-configuration.
- **Kafka Consumer**: Listens to the `ExpenseDetails` Kafka topic for expense data events published by the DS_Service.
- **Expense Management**: Supports creating, updating, and retrieving expense records.
- **MySQL Database**: Uses MySQL for persistent storage of expense data.
- **JPA & Hibernate ORM**: Implements data persistence with JPA and Hibernate for seamless ORM.
- **Custom Kafka Configuration**: Includes a dedicated `KafkaConsumerConfig` class and custom `ExpenseDeserializer` for Kafka message processing.
- **Auto-generated External IDs**: Automatically generates UUID-based external IDs for expense records on persist.
- **Default Currency Handling**: Defaults currency to `INR` when not provided.
- **RESTful API Endpoints**: Provides an endpoint for fetching expense records by user ID.

## Main Components

- **Entities**
  - `Expense`: Represents an expense record with fields: `id` (auto-generated), `externalId` (UUID, auto-generated), `userId`, `amount`, `merchant`, `currency`, and `createdAt`. Mapped to the `expense` table.

- **Repositories**
  - `ExpenseRepository`: Extends `CrudRepository` for CRUD operations on `Expense`. Provides custom query methods:
    - `findByUserId` — Retrieves all expenses for a given user.
    - `findByUserIdAndCreatedAtBetween` — Retrieves expenses within a date range.
    - `findByUserIdAndExternalId` — Retrieves a specific expense by user ID and external ID.

- **Services**
  - `ExpenseService`: Contains business logic for:
    - `createExpense` — Creates a new expense record from an `ExpenseDto`, setting default currency if not provided.
    - `updateExpense` — Updates an existing expense found by userId and externalId.
    - `getExpenses` — Retrieves all expenses for a given user ID.

- **Controllers**
  - `ExpenseController`: Exposes REST endpoints:
    - `GET /expense/v1/?user_id={userId}` — Retrieves all expenses for a specified user.

- **Kafka Consumers**
  - `ExpenseConsumer`: Listens to the `ExpenseDetails` Kafka topic. On receiving a message, it invokes `ExpenseService.createExpense` to persist the expense data.

- **Deserializers**
  - `ExpenseDeserializer`: Custom Kafka deserializer that converts byte arrays into `ExpenseDto` objects using Jackson `ObjectMapper`.

- **DTOs**
  - `ExpenseDto`: Data Transfer Object for expense data. Uses `@JsonNaming(SnakeCaseStrategy)` for snake_case JSON serialization. Fields include: `externalId`, `userId`, `amount`, `merchant`, `currency`, and `createdAt`.

- **Configuration**
  - `KafkaConsumerConfig`: Programmatically configures the Kafka `ConsumerFactory` and `ConcurrentKafkaListenerContainerFactory` with custom deserializer settings, bootstrap servers, and consumer group ID.

## Getting Started

1. **Clone the repository**
   `git clone https://github.com/vipuljain00/ExpenseService.git`

2. **Configure MySQL Database**
   Update your database connection properties in `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://<host>:3306/expenseservice
   spring.datasource.username=<username>
   spring.datasource.password=<password>
   ```

3. **Configure Kafka**
   Update Kafka connection properties in `application.properties`:
   ```properties
   spring.kafka.bootstrap-servers=<kafka-host>:9093
   spring.kafka.topic.name=ExpenseDetails
   spring.kafka.consumer.group-id=expense-service-group
   ```

4. **Build and Run**
   Build using Gradle and run the application:
   ```
   ./gradlew bootRun
   ```

5. **API Usage**
   - Fetch all expenses for a user via `GET /expense/v1/?user_id={userId}`
   - Expense records are automatically created when the DS_Service publishes events to the `ExpenseDetails` Kafka topic.

## Technologies Used

- Java 21
- Spring Boot 4.0.1
- Spring Kafka
- JPA/Hibernate
- MySQL
- Lombok
- Jackson (JSON processing)

## License

This project is currently unlicensed.

---
For any queries or contributions, please visit the [GitHub repository](https://github.com/vipuljain00/ExpenseService).
