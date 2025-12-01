package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.DatabaseUtil; // Import the new DatabaseUtil
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignUpAction {

    public boolean handleSignUpAction(String fullName, String nickName, String username, String password, String confirmPassword, String role) {
        if (!password.equals(confirmPassword)) {
            return false; // Passwords do not match
        }

        // Check if username already exists
        if (isUsernameTaken(username)) {
            System.err.println("Username already taken: " + username);
            return false;
        }

        try (Connection conn = DatabaseUtil.getConnection()) {
            // Insert user into the User table
            String insertUserSql = "INSERT INTO User (id, username, password, displayName, fullName, role) VALUES (?, ?, ?, ?, ?, ?)";
            String userId = java.util.UUID.randomUUID().toString(); // Generate a UUID for the user ID

            try (PreparedStatement pstmt = conn.prepareStatement(insertUserSql)) {
                pstmt.setString(1, userId);
                pstmt.setString(2, username);
                pstmt.setString(3, password);
                pstmt.setString(4, nickName);
                pstmt.setString(5, fullName);
                pstmt.setString(6, role.toUpperCase()); // Store role as uppercase (STUDENT or TEACHER)
                pstmt.executeUpdate();
                System.out.println("User registered: " + username);
            }

            // If it's a student, add an entry to the LeaderboardEntry table
            if ("Student".equals(role)) {
                String insertLeaderboardSql = "INSERT INTO LeaderboardEntry (id, score, userId) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertLeaderboardSql)) {
                    pstmt.setString(1, java.util.UUID.randomUUID().toString()); // Generate a UUID for leaderboard entry ID
                    pstmt.setInt(2, 0); // Initial score is 0
                    pstmt.setString(3, userId);
                    pstmt.executeUpdate();
                    System.out.println("Leaderboard entry created for student: " + username);
                }
            }
            return true;
        } catch (SQLException e) {
            System.err.println("Database error during sign-up: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM User WHERE username = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Database error checking username: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


}
