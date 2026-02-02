# Feature Backlog & Migration Plan

The following issues represent features from the previous prototype (archived) that need to be fully implemented or verified in the current Spring Boot + Compose Multiplatform architecture.

## 1. Authentication & User Management
**Label:** `feature`, `backend`, `security`
**Description:**
Implement a robust authentication system supporting distinct roles.
- [ ] **Login Endpoint**: Secure API for user authentication (JWT/Session).
- [ ] **Role-Based Access Control (RBAC)**: Enforce `STUDENT` and `TEACHER` permissions.
- [ ] **Registration**: Allow new users to sign up (or Admin to create them).
- [ ] **Seeding**: Port the CSV-based seeding logic to Spring Boot (`CommandLineRunner` or similar) to populate initial users.

## 2. Course Management (Teacher)
**Label:** `feature`, `backend`, `frontend`
**Description:**
Enable teachers to manage their curriculum.
- [ ] **Create Course**: API and UI to add new courses with descriptions.
- [ ] **List Courses**: View all courses taught by the logged-in teacher.
- [ ] **Edit/Delete Course**: Manage existing course details.

## 3. Quiz Management (Teacher)
**Label:** `feature`, `backend`, `frontend`
**Description:**
Comprehensive tools for creating assessments.
- [ ] **Create Quiz**: UI to define quiz title, duration, and number of questions.
- [ ] **Question Bank**: Add questions with options and correct answers.
- [ ] **Quiz List**: View quizzes associated with a specific course.

## 4. Student Enrollment & Dashboard
**Label:** `feature`, `backend`, `frontend`
**Description:**
The primary student interface.
- [ ] **Course Catalog**: Browse all available courses.
- [ ] **Enrollment**: Logic to allow students to join a course (prevent duplicates).
- [ ] **My Courses**: Dashboard view of enrolled courses.

## 5. Quiz Taking Engine
**Label:** `feature`, `frontend`
**Description:**
The interactive quiz experience.
- [ ] **Quiz Interface**: Timer-based UI for answering questions.
- [ ] **Submission**: securely submit answers to the backend.
- [ ] **Instant Feedback**: Calculate and display score immediately (or upon teacher release).

## 6. Leaderboard System
**Label:** `feature`, `backend`, `analytics`
**Description:**
Gamification element.
- [ ] **Score Tracking**: Record quiz scores in the database.
- [ ] **Global/Course Leaderboard**: API to fetch top-performing students.
- [ ] **UI Display**: Visual leaderboard component.

## 7. Database Migration
**Label:** `technical`, `backend`
**Description:**
Ensure the new PostgreSQL schema matches the legacy Prisma model requirements.
- [ ] **Models**: Verify `User`, `Course`, `Quiz`, `Question`, `Enrollment`, `LeaderboardEntry` entities in JPA.
- [ ] **Relations**: Ensure correct `@OneToMany` and `@ManyToOne` mappings.

## 8. Frontend Packaging & Distribution
**Label:** `technical`, `frontend`, `devops`
**Description:**
Configure the build system to generate platform-specific installers as per architectural goals.
- [ ] **Desktop Configuration**: Configure Gradle/Compose to output `.deb`, `.rpm` (Linux) and `.msi`/`.exe` (Windows).
- [ ] **Mobile Configuration**: Ensure Android release build signing and APK/AAB generation works.

## 9. API Documentation
**Label:** `technical`, `backend`
**Description:**
Implement OpenAPI/Swagger to document the REST API for the frontend team.
- [ ] **Dependency**: Add `springdoc-openapi` to `pom.xml`.
- [ ] **Configuration**: Enable Swagger UI endpoint.
- [ ] **Docs**: Annotate Controllers with `@Operation` and `@ApiResponse`.

## 10. Design System Refinement (Typography)
**Label:** `frontend`, `design`
**Description:**
Complete the Gonia-Edu design system implementation.
- [ ] **Font Integration**: Integrate "Geist Sans" (or valid fallback) into `GoniaEduTheme`.
- [ ] **Typography Scale**: Define `h1` through `body2` with correct weights and sizes in `Typography.kt`.

## 11. Backend Entity Implementation
**Label:** `backend`, `database`
**Description:**
Implement the missing JPA entities required by the schema.
- [ ] **Course Entity**: Create `Course.java` with relations to `User` (teacher).
- [ ] **Quiz Entity**: Create `Quiz.java`.
- [ ] **Question Entity**: Create `Question.java`.
- [ ] **Enrollment Entity**: Create `Enrollment.java`.
- [ ] **LeaderboardEntry Entity**: Create `LeaderboardEntry.java`.

## 12. Bug: Incorrect Role Redirection
**Label:** `bug`, `frontend`
**Description:**
Currently, logging in with a `TEACHER` account still redirects the user to the `StudentDashboard`.
- [ ] **Fix**: Implement logic in the login navigator to check user role and route to `TeacherDashboard` if the role is `TEACHER`.

## 13. Enhanced User Registration & Profile
**Label:** `feature`, `backend`, `frontend`
**Description:**
Expand the user model and signup form to collect essential academic and contact information.
- [ ] **Email (Mandatory)**: Add `email` field to `User` entity and signup UI.
- [ ] **Phone Number**: Add optional `phoneNumber` field.
- [ ] **Student Level**: For students, add a level selector (e.g., College, University).
- [x] **Email (Mandatory)**: Add `email` field to `User` entity and signup UI.
- [x] **Phone Number**: Add optional `phoneNumber` field.
- [x] **Student Level**: For students, add a level selector (e.g., College, University).
- [x] **Validation**: Ensure email is unique and valid.

## 14. Forgot Password Flow
**Label:** `feature`, `backend`, `frontend`, `security`
**Status:** `Closed`
**Description:**
Implement a secure password recovery system using email verification.
- [x] **Backend**: Integrate a mailer service (like Nodemailer or Spring Mail) to send reset tokens.
- [x] **API**: Create endpoints for requesting a reset and submitting a new password with a valid token.
- [x] **Frontend**: Build the "Forgot Password" screen and "Reset Password" form.

## 15. Bug: Forgot Password Email Not Sending & Security Vulnerability
**Label:** `bug`, `security`, `backend`
**Description:**
- [ ] **Bug**: Emails are not being received by the user.
- [ ] **Security**: Any random 6-digit code is currently working to change the password (needs validation check fix).

## 16. Technical: Backend Action Logging
**Label:** `technical`, `backend`, `audit`
**Description:**
Every major action (Login, Signup, Course Creation, Quiz Submission) must be logged in the backend for auditing purposes.
- [ ] **Implementation**: Add SLF4J/Logback logging to all Controllers and Services.
