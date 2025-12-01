package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class QuizListStudentAction {

    public static String currentCourseId; // To store the ID of the currently selected course

    public Map<String, String> getQuizzesForCourse(String courseId) {
        Map<String, String> quizMap = new HashMap<>();
        String sql = "SELECT id, title FROM Quiz WHERE courseId = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                quizMap.put(rs.getString("id"), rs.getString("title"));
            }
        } catch (SQLException e) {
            System.err.println("Database error loading quizzes for course: " + e.getMessage());
            e.printStackTrace();
        }
        return quizMap;
    }

    public static void openCourseListStudent(String courseId) throws IOException {
        QuizListStudentAction.currentCourseId = courseId; // Store the course ID
        com.quizapp.App.changeScene("/com/quizapp/QuizListStudent.fxml");
    }
}
