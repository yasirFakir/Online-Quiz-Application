package com.quizapp.Actions;

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

public class QuizListTeacherAction {
    public static String currentCourseId; // To store the ID of the currently selected course

    public Map<String, String> getQuizzesForTeacherCourse(String courseId) {
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
            System.err.println("Database error loading quizzes for teacher course: " + e.getMessage());
            e.printStackTrace();
        }
        return quizMap;
    }

    public static void openCourseListTeacher(String courseId) throws IOException {
        QuizListTeacherAction.currentCourseId = courseId;
        com.quizapp.App.changeScene("/com/quizapp/QuizListTeacher.fxml");
    }
}
