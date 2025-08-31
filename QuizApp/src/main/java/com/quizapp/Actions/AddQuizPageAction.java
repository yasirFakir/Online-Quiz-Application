package com.quizapp.Actions;

import com.quizapp.DatabaseUtil;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.UUID;

public class AddQuizPageAction {

    public void saveQuizToDatabase(String courseName, String title, List<Question> questions) {
        if (title.isEmpty()) {
            showErrorMessage("Quiz title is required.");
            return;
        }

        boolean hasValidQuestion = questions.stream()
                .anyMatch(q -> !q.getText().isEmpty() && !q.getAnswer().isEmpty());

        if (!hasValidQuestion) {
            showErrorMessage("At least one question with a correct answer must be filled.");
            return;
        }

        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Find the Course ID
            String courseId = null;
            String findCourseSql = "SELECT id FROM Course WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(findCourseSql)) {
                pstmt.setString(1, courseName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    courseId = rs.getString("id");
                } else {
                    showErrorMessage("Course not found: " + courseName);
                    return;
                }
            }

            // Insert Quiz
            String quizId = UUID.randomUUID().toString();
            String insertQuizSql = "INSERT INTO Quiz (id, title, courseId) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuizSql)) {
                pstmt.setString(1, quizId);
                pstmt.setString(2, title);
                pstmt.setString(3, courseId);
                pstmt.executeUpdate();
            }

            // Insert Questions
            String insertQuestionSql = "INSERT INTO Question (id, text, correctAnswer, options, quizId) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuestionSql)) {
                for (Question question : questions) {
                    if (!question.getText().isEmpty() && !question.getAnswer().isEmpty()) {
                        pstmt.setString(1, UUID.randomUUID().toString());
                        pstmt.setString(2, question.getText());
                        pstmt.setString(3, question.getAnswer());
                        // Combine all options into a single comma-separated string
                        String optionsString = String.join(",", question.getOption2(), question.getOption3(), question.getOption4());
                        pstmt.setString(4, optionsString);
                        pstmt.setString(5, quizId);
                        pstmt.addBatch(); // Add to batch for efficient insertion
                    }
                }
                pstmt.executeBatch(); // Execute all batched inserts
            }

            conn.commit(); // Commit transaction
            showSuccessMessage("Quiz \"" + title + "\" saved successfully to database!");

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
            showErrorMessage("Error saving quiz to database: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    // Updated Question class to match database schema
    public static class Question {
        private String text;
        private String answer;
        private String option2;
        private String option3;
        private String option4;

        public Question(String text, String answer, String option2, String option3, String option4) {
            this.text = text;
            this.answer = answer;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }
    }
}