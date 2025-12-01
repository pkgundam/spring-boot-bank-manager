# Spring Boot Bank Manager – Spring Security + JPA (No Tests)

This branch implements a fully functional banking backend with:

- JWT Authentication (Access + Refresh Tokens)
- Role-based Authorization (USER / ADMIN)
- Master-key protected Admin creation
- PostgreSQL persistence using JPA/Hibernate
- Transactional integrity
- Per-user account permissions
- Transaction history tracking
- Clean layered architecture (controller/service/repository/entity)
- Custom annotations: `@CurrentUser`, `@IsUser`, `@IsAdmin`
- Global Exception Handling

---

## 🚀 Features

### 🔐 Authentication
- User registration
- User login
- Refresh tokens (secure & hashed)
- Create-first-admin (master-key protected)

### 💳 Banking
- Create account (multiple accounts per user)
- Deposit
- Withdraw
- Transfer money between accounts
- Transaction history per account
- View all accounts (ADMIN only)

---

## 🛠 Technologies Used
- Spring Boot 3.5.7
- Spring Security 6 + JWT
- PostgreSQL (Neon DB or local)
- Spring Data JPA
- Hibernate ORM
- Lombok
- Jakarta Validation
- Method Security (`@EnableMethodSecurity`)
- Custom Security Utilities

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

### 2. Add environment variables:
```
MASTER_KEY=your-master-key
JWT_SECRET=your-32byte-secret
DB_HOST=
DB_NAME=
DB_PASSWORD=
DB_USERNAME=
```
Add then in environment variables in IntelliJ
In actual production systems, these are handled differently by retrieving from vaults from AWS Secrets Manager, Hashicorp etc.

# 🧪 Access Rules

| Endpoint Group | Security |
|----------------|----------|
| `/api/auth/**` | Public |
| `/api/**` | Authenticated |
| `/api/admin/**` | ADMIN only |
| Banking APIs | USER-only unless admin |

## 🧪 API Coverage (High-level)
| Area | Status |
|------|--------|
| Registration/Login | ✅ |
| JWT Security | ✅ |
| Refresh tokens | ✅ |
| CRUD accounts | ✅ |
| Deposit/Withdraw | ✅ |
| Transfers | ✅ |
| Transaction history | ✅ |
| Admin-only APIs | ✅ |

---