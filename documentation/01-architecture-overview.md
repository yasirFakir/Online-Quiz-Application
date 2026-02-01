# 01 - Architecture Overview

## System Philosophy
The Online Quiz Application is built as a high-precision, cross-platform educational tool. It follows a "Single Project, Dual Target" philosophy using Compose Multiplatform for the frontend and a robust Spring Boot backend.

## High-Level Diagram
```text
[ Desktop App (Win/Linux) ]    [ Mobile App (Android) ]
            \                      /
             \---[ Shared UI ]----/
                   (packages/ui)
                        |
                        | (REST API / JSON)
                        |
               [ Spring Boot API ]
                   (backend)
                        |
               [ PostgreSQL DB ]
```

## Project Structure
*   **backend/**: The Spring Boot 3.2 application. Handles business logic, security, and data persistence.
*   **frontend/apps/**: Platform-specific entry points (Desktop and Mobile). These are minimal shells.
*   **frontend/packages/ui/**: **The core of the frontend.** Contains all shared screens (Login, Dashboards, Quiz), components, and the Gonia-Edu theme.
*   **frontend/packages/core/**: Shared business logic for the frontend, such as API client services and data models.
*   **documentation/**: Technical guides and architectural decision logs.
*   **progress/**: Active tracking of development milestones.

## Key Technologies
*   **Language**: Java (Backend), Kotlin (Frontend/Shared).
*   **Backend**: Spring Boot, Spring Data JPA, Hibernate.
*   **Frontend**: JetBrains Compose Multiplatform.
*   **Database**: PostgreSQL 15.
*   **Design**: Gonia-Edu (Geometric, 0rem-radius, Academic focus).
