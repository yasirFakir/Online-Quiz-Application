# 03 - Frontend Deep Dive (Compose Multiplatform)

## Concept: Shared UI
The most critical part of this project is the **Shared UI**. Instead of writing separate apps for Desktop and Mobile, we write the screens once in `packages/ui/src/commonMain`.

## Frontend Modules
### 1. `packages/ui` (Shared)
*   **Theme**: `com.quiz.ui.theme` defines the Gonia-Edu aesthetic (Colors, Shapes).
*   **Screens**: `com.quiz.ui.screens` contains the Login, Student Dashboard, Teacher Dashboard, etc.
*   **Components**: `com.quiz.ui.components` contains reusable atoms like `GoniaEduButton`.

### 2. `apps/desktop` (Launcher)
A minimal JVM application that creates a `Window` and loads the shared UI. It targets:
*   **Linux**: Packaged as `.deb` or `.rpm`.
*   **Windows**: Packaged as `.msi` or `.exe`.

### 3. `apps/mobile` (Launcher)
An Android application that loads the shared UI in an `Activity`.

## Data Management
The frontend communicates with the Spring Boot backend via a shared API client located in `packages/core`. This ensures that both Desktop and Mobile use the exact same logic for logging in and fetching quizzes.
