# Crypto Payment API

A backend REST API built with **Spring Boot** demonstrating real-world patterns used in fintech and Web3 infrastructure:

- **Redis** — response caching and rate limiting
- **RabbitMQ** — async payment queue with background worker
- **MongoDB** — payment persistence
- **Web3j** — live Ethereum blockchain integration (wallet balance lookup)

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 17 |
| Framework | Spring Boot 3.2 |
| Caching | Redis + Spring Cache (`@Cacheable`) |
| Message Queue | RabbitMQ + Spring AMQP |
| Database | MongoDB + Spring Data |
| Blockchain | Web3j + Alchemy (Ethereum) |
| Build Tool | Maven |

---

## Getting Started

### Prerequisites
- Java 17+
- Maven
- Docker (for Redis, RabbitMQ, MongoDB)
- Free [Alchemy](https://www.alchemy.com) account

### 1. Start infrastructure with Docker

```bash
docker-compose up -d
```

This starts Redis on `6379`, RabbitMQ on `5672` (UI at http://localhost:15672), and MongoDB on `27017`.

### 2. Add your Alchemy key

Open `src/main/resources/application.yml` and replace:

```yaml
app:
  alchemy-url: https://eth-mainnet.g.alchemy.com/v2/YOUR_ALCHEMY_KEY_HERE
```

> ⚠️ Never commit a real API key to GitHub. Use environment variables in production.

### 3. Run the application

```bash
mvn spring-boot:run
```

The API starts on http://localhost:8080

---

## API Reference

### Wallet

#### Get ETH balance
```
GET /api/wallet/{address}/balance
```
First call fetches from Ethereum. Subsequent calls within 5 minutes are served from **Redis cache**.

```bash
curl http://localhost:8080/api/wallet/0xde0B295669a9FD93d5F28D9Ec85E40f4cb697BAe/balance
```

Response:
```json
{
  "address": "0xde0B295669a9FD93d5F28D9Ec85E40f4cb697BAe",
  "balanceEth": 123.456789,
  "balanceWei": "123456789000000000000",
  "cached": false
}
```

---

### Payments

#### Submit a payment
```
POST /api/payments
```
Saves the payment as `PENDING` and publishes it to **RabbitMQ**. Returns immediately — processing is async.

```bash
curl -X POST http://localhost:8080/api/payments \
  -H "Content-Type: application/json" \
  -d '{
    "senderAddress":    "0xYourWalletAddress",
    "recipientAddress": "0xRecipientWalletAddress",
    "amount":           0.05,
    "currency":         "ETH"
  }'
```

Response `202 Accepted`:
```json
{
  "id": "64abc123...",
  "status": "PENDING",
  "amount": 0.05,
  "currency": "ETH"
}
```

#### Check payment status
```
GET /api/payments/{id}
```
Poll this endpoint after submitting. Status moves: `PENDING → PROCESSING → COMPLETED`.

#### Get payments by sender
```
GET /api/payments?sender=0xYourWalletAddress
```

---

## Architecture Flow

```
Client
  │
  ▼
POST /api/payments
  │
  ▼
PaymentService
  ├── Save to MongoDB (status: PENDING)
  └── Publish to RabbitMQ exchange
            │
            ▼
      PaymentWorker (background thread)
            ├── Update status → PROCESSING
            ├── [In production: broadcast tx via Web3j]
            ├── Update status → COMPLETED + txHash
            └── Save to MongoDB
```

---

## Key Patterns Demonstrated

| Pattern | Where |
|---|---|
| Redis caching with TTL | `WalletService.getBalance()` → `@Cacheable` |
| Async message queue | `PaymentService` publishes → `PaymentWorker` consumes |
| RESTful API design | `PaymentController`, `WalletController` |
| Repository pattern | `PaymentRepository extends MongoRepository` |
| Blockchain RPC call | `Web3j.ethGetBalance()` via Alchemy |
| Input validation | `@Valid`, `@Pattern`, `@NotBlank` on DTOs |
