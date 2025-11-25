# Simple Banking System --- Spring Boot (In-Memory)

This project is a beginner-friendly Spring Boot application that
simulates basic banking operations using REST APIs.\
There is **no database** used---everything runs in memory using
Maps/Lists.\
The goal is to help students learn Spring Boot fundamentals such as:

-   Controllers
-   Services
-   Repositories
-   DTOs
-   Validations
-   Exception handling
-   Layered architecture

------------------------------------------------------------------------

## 🚀 Features

-   Create a bank account\
-   View a single account\
-   View all accounts\
-   Deposit money\
-   Withdraw money\
-   Transfer money between accounts\
-   Maintain transaction history for each operation\
-   Fetch transaction history for a specific account\
-   In-memory repository (no DB required)\
-   Validation & global exception handling\
-   Clean REST API responses\
-   Health check endpoint

------------------------------------------------------------------------

## 📁 Project Structure (Packages)

    com.example.banking
     ├── controller
     ├── service
     │    └── impl
     ├── repository
     ├── model
     ├── dto
     ├── exception

------------------------------------------------------------------------

## ⚙️ How to Run

1.  Install **Java 21+** and **Maven**.\
2.  Run the application:

``` bash
mvn spring-boot:run
```

3.  The API will be available at:

```{=html}
<!-- -->
```
    http://localhost:8081

------------------------------------------------------------------------

## 🔥 API Endpoints (Quick Reference)

Action                Method   Endpoint
  --------------------- -------- -----------------------------------
- Create Account        POST     `/api/accounts`
- Get Account           GET      `/api/accounts/{id}`
- Get All Accounts      GET      `/api/accounts`
- Deposit               POST     `/api/accounts/{id}/deposit`
- Withdraw              POST     `/api/accounts/{id}/withdraw`
- Transfer              POST     `/api/accounts/transfer`
- Transaction History   GET      `/api/accounts/{id}/transactions`
- Health Check          GET      `/api/health`

------------------------------------------------------------------------

## 🧱 Technologies Used

-   Java 21+\
-   Spring Boot\
-   Maven\
-   Spring Web\
-   Bean Validation (Jakarta Validation)\
-   In-memory storage (Map/List)

------------------------------------------------------------------------

## 📌 Requirements Summary

-   Use layered architecture (controller → service → repository)\
-   Use DTOs for request/response\
-   Validate inputs with Bean Validation\
-   Implement custom exceptions\
-   Store and return transaction history\
-   Use meaningful HTTP status codes\
-   Use `@ControllerAdvice` for global error handling

------------------------------------------------------------------------

## 🧭 What You Should Focus On

-   Clean code & proper separation of responsibilities\
-   Consistent error responses\
-   Proper validation rules\
-   Naming conventions\
-   Handling edge cases\
-   Returning correct HTTP statuses

------------------------------------------------------------------------

# 📝 Evaluation Criteria (LLM Review)

After submission, an LLM will review and score your project based on the
following areas:

### **1. Correctness (40%)**

-   Do all APIs work as expected?
-   Are deposits, withdrawals, transfers, and transaction history
    functional?
-   Are validations implemented correctly?

### **2. Code Structure & Architecture (25%)**

-   Proper layering (controller/service/repository)
-   Use of DTOs
-   Clean, maintainable code

### **3. Error Handling & Validation (20%)**

-   Use of `@Valid` and Bean Validation rules
-   Clear and consistent error messages
-   Correct mapping of exceptions to HTTP status codes

### **4. API Design & Clarity (10%)**

-   Meaningful endpoint naming
-   Clean JSON responses
-   Correct HTTP verbs and status codes

### **5. Documentation & Organization (5%)**

-   README clarity
-   Proper package structure
-   Easy to run and understand

------------------------------------------------------------------------

## 📊 How Scoring Works

-   Total score: **100 points**
-   Each category contributes based on the percentages above
-   The LLM will provide:
    -   Score breakdown\
    -   Strengths\
    -   Areas to improve\
    -   Final evaluation summary

------------------------------------------------------------------------

## ✔️ Good Luck!

Focus on writing clean, readable code and structuring your project
well.\
This assignment is meant to teach you real-world API development the
right way from the very beginning.
