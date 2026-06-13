# Razorpay Clone (LEARNING)

A Spring Boot 3/4 based backend implementation of a payment gateway system (similar to Razorpay). This project is designed as a learning resource to model clean domain design, JPA entities, secure card vaulting, payment state transitions, settlements, and webhook delivery.

---

## 🚀 Tech Stack

- **Java**: 21
- **Framework**: Spring Boot 4.0.6
- **Persistence**: Spring Data JPA & Hibernate 6
- **Database**: PostgreSQL
- **Utilities**: Project Lombok
- **Testing**: Spring Boot Starter Test (JUnit 5, Mockito)

---

## 📁 Project Structure & Domain Subsystems

The application is structured into domain-driven subsystems within `com.razororpay.razorpayClone`:

```
com.razororpay.razorpayClone
├── RazorpayCloneApplication.java
├── common/
│   ├── entity/
│   │   └── Money.java              # Embedded Value Object (amount units + currency)
│   └── enums/                      # Project-wide state and configuration enums
├── merchant/
│   └── entity/
│       ├── Merchant.java           # Business account entity
│       ├── AppUser.java            # User credentials and role-based access
│       ├── Customer.java           # End-customers of merchants
│       ├── ApiKey.java             # API access credentials (environment specific)
│       └── MerchantWebhookConfig.java # Webhook endpoint settings
├── payment/
│   └── entity/
│       ├── OrderRecord.java        # Orders created by merchants
│       ├── Payment.java            # Detailed payment transaction details
│       ├── PaymentTransitionLog.java # Audit logs of payment state transitions
│       └── Refund.java             # Refunds processed on captured payments
├── vault/
│   └── entity/
│       ├── VaultCard.java          # Raw card details securely vaulted and encrypted
│       └── CardToken.java          # Tokenized representation of vaulted cards
└── operations/
    └── entity/
        ├── Settlement.java         # Merchant payouts and financial settlements
        ├── SettlementPayment.java  # Mapping table for payments processed in a settlement
        ├── SettlementPaymentId.java# Composite primary key for SettlementPayment
        ├── WebhookEvent.java       # Outbound webhook notifications
        └── DlqEvent.java           # Dead Letter Queue for failed webhooks
```

---

## 🛠️ Database Design & Entity Overview

### 1. Common Value Objects & Enums
- **`Money`**: An `@Embeddable` type containing `amountUnits` (represented as integers to avoid floating-point errors) and `currency`.
- **Enums**:
  - `PaymentStatus` (e.g., `CREATED`, `AUTHORIZED`, `CAPTURED`, `REFUNDED`, `FAILED`, `SETTLED`)
  - `PaymentEvent` (triggers for state transitions)
  - `PaymentActor` (`CUSTOMER`, `MERCHANT`, `SYSTEM`)
  - `RefundStatus` (`PENDING`, `PROCESSING`, `PROCESSED`, `FAILED`)
  - `OrderStatus` (`CREATED`, `PAID`, `EXPIRED`)

### 2. Merchant Domain
- **`Merchant`**: Tracks onboarding status, business type, and company details.
- **`AppUser`**: Users belonging to a merchant with roles (e.g., `MERCHANT_ADMIN`).
- **`ApiKey`**: Supports API keys for different environments (`SANDBOX`, `PRODUCTION`).

### 3. Payment Processing
- **`OrderRecord`**: A merchant-created order to accept a payment.
- **`Payment`**: Tracks the full lifecycle of a card, UPI, or net-banking transaction. Uses PostgreSQL `jsonb` for dynamic method details.
- **`PaymentTransitionLog`**: Event-sourced/audit log tracking status movements from one state to another (e.g. `AUTHORIZED` ➔ `CAPTURED`).
- **`Refund`**: Created against a captured payment. Handles partial and full refunds.

### 4. Card Vaulting
- **`VaultCard`**: Stores card credentials securely with encrypted PAN and DEK (Data Encryption Key) along with expiry and metadata.
- **`CardToken`**: References vaulted cards for repeating payments or tokenized compliance.

### 5. Payouts & Webhooks
- **`Settlement`**: Aggregates net amounts (gross amount - refunds - fees - gst) for automatic transfer to a merchant's bank account.
- **`SettlementPayment`**: Join table mapping payments to their respective settlements.
- **`WebhookEvent`**: Dispatches asynchronous payment events to merchant servers. Includes signature validation headers and retry capabilities.
- **`DlqEvent`**: Dead Letter Queue entries for events that failed to deliver after maximum retry attempts.

---

## ⚙️ Configuration & Getting Started

### Prerequisites
1. **Java 21 JDK** installed.
2. **PostgreSQL** database running locally or remotely.

### Database Setup
Create a PostgreSQL database named `razorpay_db`:
```sql
CREATE DATABASE razorpay_db;
```

Update your configuration parameters in `src/main/resources/application.yaml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/razorpay_db
    username: <your-database-username>
    password: <your-database-password>
```

---

## 💻 Running Commands

All common lifecycle commands can be executed using the Maven wrapper:

### Compile the Code
```bash
./mvnw clean compile
```

### Run Tests
```bash
./mvnw test
```

### Start the Application
```bash
./mvnw spring-boot:run
```
