# AI Context & Architecture Rules for Online Quiz Application

**SYSTEM PROMPT FOR AI ASSISTANTS:**
When generating code for this project, you **MUST** strictly adhere to the following architectural patterns and constraints. Read this entire file before proposing changes.

---

## 1. Tech Stack Overview
*   **Backend:** Spring Boot 3.2+ (Java 17+), Spring Data JPA, PostgreSQL (Primary DB).
*   **Frontend:** Compose Multiplatform (Kotlin) for Desktop (Linux/Windows) and Mobile (Android).
*   **Architecture:** Monorepo organization. `packages/ui` contains shared Gonia-Edu components.
*   **Database:** PostgreSQL for scalability and production readiness.

---

## 2. Design System: Gonia-Edu (Professional & Academic)

**Gonia-Edu** is a specialized variant of Gonia designed for high-precision educational tools.

### Visual Rules
*   **Geometry**: **Zero border radius (`0rem`)** everywhere. 
*   **Palette (Gonia-Edu)**:
    *   Primary: **Sage Green (#74B086)** - From "Student Studying" illustration.
    *   Secondary: **Mint Green (#9BD4AD)**.
    *   Accent: **Dark Charcoal (#263135)**.
    *   Background: **Mint White (#F3FCF6)**.
    *   Border: **Light Gray-Green (#DAE6DF)**.
*   **Borders**: Technical 1px borders.
*   **Interactions**: Buttons are height `44dp`, rectangle shape, flat elevation.
*   **Layout**: Split-screen 50/50 for Authentication pages, with illustration taking 70% of its container.

---

# Project State Snapshot

<state_snapshot>
    <overall_goal>
        Build a professional Online Quiz Application with a Spring Boot/PostgreSQL backend and a unified Compose Multiplatform frontend for Desktop and Mobile.
    </overall_goal>

    <key_knowledge>
        - **Unified UI**: 95% of UI code is shared in `packages/ui`.
        - **Backend**: Spring Boot configured for PostgreSQL (migrated from Prisma/SQLite).
        - **Features Implemented (Legacy/To-Migrate)**:
            - Authentication & RBAC (Student/Teacher).
            - Course & Quiz Management.
            - Student Enrollment & Quiz Taking.
            - Leaderboard System.
        - **Issue Tracking**: Features detailed in `documentation/GITHUB_ISSUES.md`.
    </key_knowledge>

    <recent_actions>
        - **Cleanup**: Removed `temp_archive` and legacy logs.
        - **Feature Documentation**: Extracted legacy features into `GITHUB_ISSUES.md`.
        - **Design Update**: Updated Theme to "Sage Green" based on "Student-Studying.jpg".
        - **Layout Update**: Implemented 50/50 split for Login/Signup screens.
        - **Asset Management**: Added `login_illustration.jpg` to resources.
        - **Backend Expansion**: Implemented JPA entities (Course, Quiz, Question, etc.) and Spring Mail integration.
        - **Auth Enhancement**: Updated User model and Signup UI with email, phone, and student level fields.
        - **Security**: Implemented a 6-digit verification code flow for password resets.
    </recent_actions>
</state_snapshot>