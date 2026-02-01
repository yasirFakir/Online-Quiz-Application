# 02 - Backend Deep Dive

## Framework & Environment
*   **Java Version**: 17
*   **Build Tool**: Maven (`mvnw`)
*   **Port**: 8080 (Default)

## Database: PostgreSQL
The application uses PostgreSQL for production-grade reliability. 
*   **Database Name**: `quizdb`
*   **Username/Password**: `postgres` / `postgres` (Local development)
*   **Schema Strategy**: `update` (Hibernate automatically manages table creation during development).

## Layered Architecture
1.  **Entity**: Located in `com.quiz.backend.entity`. Maps directly to database tables.
2.  **Repository**: Located in `com.quiz.backend.repository`. Interfaces for database operations using Spring Data JPA.
3.  **Service**: Located in `com.quiz.backend.service`. Contains the business logic (e.g., calculating quiz scores, managing user sessions).
4.  **Controller**: Located in `com.quiz.backend.controller`. Exposes REST API endpoints.
5.  **DTO**: Located in `com.quiz.backend.dto`. Data objects used for API requests and responses to keep entities internal and secure.

## API Standards
*   All endpoints return JSON.
*   Standard HTTP status codes are used (200 OK, 201 Created, 401 Unauthorized, etc.).
*   Authentication is handled via JWT or standard session-based auth (to be finalized).
