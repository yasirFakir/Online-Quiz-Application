package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.Controllers.EnrollPageController;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public class EnrollAction {

    public void enrollCourse(String courseName, String courseDescription) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();

            // Get student ID
            String studentId = null;
            String getStudentIdSql = "SELECT id FROM User WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(getStudentIdSql)) {
                pstmt.setString(1, App.username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    studentId = rs.getString("id");
                }
            }

            if (studentId == null) {
                showAlert("Error", "Student not found.");
                return;
            }

            // Get course ID
            String courseId = null;
            String getCourseIdSql = "SELECT id FROM Course WHERE name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(getCourseIdSql)) {
                pstmt.setString(1, courseName);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    courseId = rs.getString("id");
                }
            }

            if (courseId == null) {
                showAlert("Error", "Course not found.");
                return;
            }

            // Check if already enrolled
            String checkEnrollmentSql = "SELECT COUNT(*) FROM Enrollment WHERE studentId = ? AND courseId = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(checkEnrollmentSql)) {
                pstmt.setString(1, studentId);
                pstmt.setString(2, courseId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    showAlert("Already Enrolled", "You are already enrolled in: " + courseName);
                    return;
                }
            }

            // Enroll student
            String insertEnrollmentSql = "INSERT INTO Enrollment (id, studentId, courseId, score) VALUES (?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertEnrollmentSql)) {
                pstmt.setString(1, java.util.UUID.randomUUID().toString());
                pstmt.setString(2, studentId);
                pstmt.setString(3, courseId);
                pstmt.setInt(4, 0); // Initial score is 0
                pstmt.executeUpdate();
                showAlert("Enrollment Successful", "You have successfully enrolled in: " + courseName);
            }

        } catch (SQLException e) {
            System.err.println("Database error during enrollment: " + e.getMessage());
            e.printStackTrace();
            showAlert("Error", "An error occurred during enrollment.");
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void openEnrollPage(javafx.scene.control.Button currentButton) throws IOException {
        App.closeCurrentWindow(currentButton); // Close the current window

        FXMLLoader fxmlLoader = new FXMLLoader(EnrollPageController.class.getResource("/com/quizapp/Enroll.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(EnrollPageController.class.getResource("/css/style.css")).toExternalForm());

        Stage enrollStage = new Stage();
        enrollStage.setTitle("Enroll in a Course");
        enrollStage.setMaximized(true);
        enrollStage.setScene(scene);
        enrollStage.show();
    }
}
