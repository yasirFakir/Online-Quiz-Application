package com.quizapp.Actions;

import com.quizapp.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class QuizEditPageAction {

    public List<Question> loadQuizData(String quizId) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT text, correctAnswer, options FROM Question WHERE quizId = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, quizId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String text = rs.getString("text");
                String correctAnswer = rs.getString("correctAnswer");
                String optionsString = rs.getString("options");

                // Split the options string back into individual options
                String[] optionsArray = optionsString.split(",");
                String option2 = optionsArray.length > 0 ? optionsArray[0] : "";
                String option3 = optionsArray.length > 1 ? optionsArray[1] : "";
                String option4 = optionsArray.length > 2 ? optionsArray[2] : "";

                questions.add(new Question(text, correctAnswer, option2, option3, option4));
            }
        } catch (SQLException e) {
            System.err.println("Database error loading quiz data: " + e.getMessage());
            e.printStackTrace();
        }
        return questions;
    }

    public void updateQuizInDatabase(String quizId, String quizTitle, List<Question> questions) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Update Quiz title
            String updateQuizSql = "UPDATE Quiz SET title = ? WHERE id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(updateQuizSql)) {
                pstmt.setString(1, quizTitle);
                pstmt.setString(2, quizId);
                pstmt.executeUpdate();
            }

            // Delete existing questions for this quiz
            String deleteQuestionsSql = "DELETE FROM Question WHERE quizId = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteQuestionsSql)) {
                pstmt.setString(1, quizId);
                pstmt.executeUpdate();
            }

            // Insert new questions
            String insertQuestionSql = "INSERT INTO Question (id, text, correctAnswer, options, quizId) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertQuestionSql)) {
                for (Question question : questions) {
                    if (!question.getText().isEmpty() && !question.getAnswer().isEmpty()) {
                        pstmt.setString(1, UUID.randomUUID().toString());
                        pstmt.setString(2, question.getText());
                        pstmt.setString(3, question.getAnswer());
                        String optionsString = String.join(",", question.getOption2(), question.getOption3(), question.getOption4());
                        pstmt.setString(4, optionsString);
                        pstmt.setString(5, quizId);
                        pstmt.addBatch();
                    }
                }
                pstmt.executeBatch();
            }

            conn.commit(); // Commit transaction
            System.out.println("Quiz \"" + quizTitle + "\" updated successfully in database!");

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback(); // Rollback on error
            } catch (SQLException ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
            System.err.println("Database error updating quiz: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

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