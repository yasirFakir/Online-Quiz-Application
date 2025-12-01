package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.DatabaseUtil; // Import the new DatabaseUtil
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException; // Keep IOException for FXML loading
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginAction {

    // Handle the login logic
    public void handleLoginAction(String username, String password, Label messageLabel, Button loginButton) throws IOException {
        // The handleLogin method now directly returns the role based on database verification
        int loginResult = handleLogin(username, password);

        if (loginResult == 1) {  // Teacher login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a teacher.");
            openTeacherMain();  // Open teacher window
        } else if (loginResult == 2) {  // Student login
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Login successful!\nYou are a student.");
            openStudentMain(); // Open student window
        } else {  // Invalid login
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Invalid username or password.");
        }
    }

    // Logic for handling login
    public int handleLogin(String username, String password) {
        // Call the refactored verifyCredentials method
        return verifyCredentials(username, password);
    }

    // Method to open the teacher main window
    public static void openTeacherMain() throws IOException {
        App.changeScene("/com/quizapp/TeacherMain.fxml");
    }

    // Method to open the student main window
    public static void openStudentMain() throws IOException {
        App.changeScene("/com/quizapp/StudentMain.fxml");
    }

    // Refactored method to verify credentials against the database
    public int verifyCredentials(String username, String password) {
        String sql = "SELECT id, username, password, role FROM User WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Credentials are valid, now check the role
                App.username = rs.getString("username"); // Set the global username
                String role = rs.getString("role");
                if ("TEACHER".equals(role)) {
                    return 1; // Teacher
                } else if ("STUDENT".equals(role)) {
                    return 2; // Student
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error during login: " + e.getMessage());
            e.printStackTrace();
        }
        return 0; // Invalid credentials or database error
    }

    public static void logout() throws IOException {
        App.username = null; // Clear the logged-in user
        App.changeScene("/com/quizapp/LoginPageUI.fxml");
    }
}
