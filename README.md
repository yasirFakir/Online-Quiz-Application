# Online Quiz Application (QUIZE)

A professional, high-precision educational platform built with a **Spring Boot** backend and a **Compose Multiplatform** frontend for Desktop (Windows/Linux) and Mobile (Android).

## 📚 Documentation

This project follows a "Handbook" style documentation to ensure consistency and clarity. All technical details reside in the `documentation/` folder:

*   **[01. Architecture Overview](./documentation/01-architecture-overview.md)**: Details on the "Single Project, Dual Target" philosophy and system map.
*   **[02. Backend Deep Dive](./documentation/02-backend-deep-dive.md)**: Detailed guide to the Spring Boot API, PostgreSQL integration, and Layered Architecture.
*   **[03. Frontend Deep Dive](./documentation/03-frontend-deep-dive.md)**: Guide to the Compose Multiplatform structure and how the shared UI works.
*   **[04. Design Principles](./documentation/04-design-principles.md)**: Explanation of the **Gonia-Edu** UI philosophy and geometric standards.
*   **[ADL. Architectural Decisions](./documentation/01-architectural-decisions.md)**: Log of major technical choices and their rationale.

---

## 🚀 Quick Start

### 1. Environment Setup
Initialize the local environment folders and configuration:
```bash
./setup_env.sh
```

### 2. Database
The application is configured for **PostgreSQL**. Ensure you have a Postgres instance running (or use Docker).
Update `.env` with your credentials if they differ from the defaults.

### 3. Backend (Spring Boot)
```bash
cd backend
./mvnw spring-boot:run
```
*   **API Base:** http://localhost:8080

### 4. Frontend (Desktop App)
```bash
cd frontend
./gradlew :desktop:run
```
*This will launch the Windows/Linux desktop window using the shared UI logic.*

---

## 🏗️ Project Structure

The project is organized as a unified monorepo to maximize code reuse:

```text
/
├── backend/             # Spring Boot Application (Java)
├── frontend/            # Compose Multiplatform Root (Kotlin)
│   ├── apps/
│   │   ├── desktop/     # Windows/Linux Launcher shell
│   │   └── mobile/      # Android Launcher shell
│   └── packages/
│       ├── ui/          # SHARED: All Screens (Login, Dashboards, Quiz) & Theme
│       └── core/        # SHARED: API Clients & Data Models
└── documentation/       # Technical Guides & ADLs
```

---

## 🎨 Design System: Gonia-Edu

This application utilizes **Gonia-Edu**, a high-precision design language adapted for educational tools:
- **Geometry**: Strictly **0rem** (rectangle) border radius for all components.
- **Palette**: Sage Green (#74B086), Mint Green (#9BD4AD), and Dark Charcoal (#263135).
- **Typography**: Clean, high-readability sans-serif (Geist Sans).
- **Feel**: Professional, geometric, and nature-inspired (Studying).

---

## 🧪 Development Workflow

### Shared UI Development
95% of the frontend code is located in `frontend/packages/ui/src/commonMain`. When you make a change here, it is automatically reflected in both the Desktop and Mobile applications.

### Backend Development
The backend follows a standard layered architecture:
- `Controller` -> `Service` -> `Repository` -> `Entity`.
- Data is transferred via DTOs to ensure API security.
