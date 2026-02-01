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
