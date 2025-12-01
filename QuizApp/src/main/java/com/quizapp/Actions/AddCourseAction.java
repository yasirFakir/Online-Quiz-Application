package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.DatabaseUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AddCourseAction {

    public boolean createCourse(String courseTitle, String courseDescription) {
        if (courseTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Course title is required.");
            return false;
        }

        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();

            // Check if a course with this title already exists
            String checkSql = "SELECT COUNT(*) FROM Course WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkSql)) {
                pstmt.setString(1, courseTitle);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert(Alert.AlertType.ERROR, "Error", "A course with this title already exists.");
                    return false;
                }
            }

            // Get teacher ID
            String teacherId = null;
            String getTeacherIdSql = "SELECT id FROM User WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(getTeacherIdSql)) {
                pstmt.setString(1, App.username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    teacherId = rs.getString("id");
                }
            }

            if (teacherId == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Teacher not found. Cannot create course.");
                return false;
            }

            // Insert the new course
            String insertSql = "INSERT INTO Course (id, name, description, teacherId) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                pstmt.setString(1, UUID.randomUUID().toString());
                pstmt.setString(2, courseTitle);
                pstmt.setString(3, courseDescription);
                pstmt.setString(4, teacherId);
                pstmt.executeUpdate();
            }

            showAlert(Alert.AlertType.INFORMATION, "Success", "Course created successfully.");
            return true;
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create course: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}