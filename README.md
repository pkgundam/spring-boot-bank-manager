# Spring Boot Bank Manager — Spring Security (JWT + Roles + Admin) - No Tests

This project is a full **enterprise-style Spring Boot backend** for a simple banking system.
Students learn real-world backend development including layered architecture, authentication, authorization, JWT, filters, interceptors, admin flows, and clean exceptions.

Everything is implemented **in-memory** for learning (no database yet).

---

# 🚀 Features Implemented

## 🏦 Banking Features
- Create bank accounts
- Deposit, withdraw, transfer
- View account details
- View transaction history
- Transaction logging for all actions
- Account ownership rules (each account belongs to a user)
- Users can only access their own accounts
- Admins can access all accounts

---

## 🔐 Authentication & Authorization (Enterprise-Grade)
- User registration (`POST /api/auth/register`)
- User login (`POST /api/auth/login`)
- JWT Access Token + Refresh Token
- Secure password hashing (BCrypt)
- Stateless JWT Security
- Custom JWT authentication filter
- Custom authentication entrypoint (401 JSON)
- Custom access denied handler (403 JSON)
- Full global exception handling

---

## 🛂 Custom Annotations
To simplify controllers and remove boilerplate:

### `@CurrentUser`
Injects authenticated `CustomUserDetails` automatically.

### `@IsUser`
Restricts endpoint to authenticated users.

### `@IsAdmin`
Restricts endpoint to ADMIN role only.

This makes controllers extremely clean and enterprise-style.

---

## 🧭 Admin Features
Accessible only by `ROLE_ADMIN`:

- Get all users
- Get user by ID
- Disable user
- Enable user
- Promote user to admin
- Demote admin to user

---

## 🧰 Filters & Interceptors (Cross-Cutting Concerns)

### Filters (Servlet Level)
- `RequestLoggingFilter` — logs each incoming request
- `JwtAuthenticationFilter` — validates JWT & sets SecurityContext

### Interceptors (Spring MVC Level)
- `RequestTimingInterceptor` — logs execution time per request
- `UserActivityInterceptor` — logs authenticated user activity

---

# ⚙️ How to Run

1. Install **Java 17+**
2. Clone the project
3. Run:

```bash
mvn spring-boot:run
```

App starts at:  
`http://localhost:8081`

---

# 🔑 Authentication Flow

1. Register
2. Login
3. Use access token
4. Refresh token if expired

---

# 🧪 Access Rules

| Endpoint Group | Security |
|----------------|----------|
| `/api/auth/**` | Public |
| `/api/**` | Authenticated |
| `/api/admin/**` | ADMIN only |
| Banking APIs | USER-only unless admin |

Add then in environment variables in IntelliJ

MASTER_KEY=super-secret-value;JWT_SECRET=THIS_IS_NOT_SECURE_CHANGE_ME_32BYTES_MINIMUM_123456

In actual production systems, these are handled differently by retrieving from vaults from AWS Secrets Manager, Hashicorp etc.


