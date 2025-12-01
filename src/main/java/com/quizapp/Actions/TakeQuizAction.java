package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.Controllers.TakeQuizPageController;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class TakeQuizAction {
    public static String currentQuizId; // To store the ID of the currently selected quiz

    public void updateLeaderboardScore(int scoreToAdd) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();

            // Get user ID
            String userId = null;
            String getUserIdSql = "SELECT id FROM User WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(getUserIdSql)) {
                pstmt.setString(1, App.username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    userId = rs.getString("id");
                }
            }

            if (userId == null) {
                System.err.println("User not found for leaderboard update: " + App.username);
                return;
            }

            // Check if leaderboard entry exists for the user
            String checkLeaderboardSql = "SELECT id, score FROM LeaderboardEntry WHERE userId = ?";
            int currentScore = 0;
            String leaderboardEntryId = null;

            try (PreparedStatement pstmt = conn.prepareStatement(checkLeaderboardSql)) {
                pstmt.setString(1, userId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    currentScore = rs.getInt("score");
                    leaderboardEntryId = rs.getString("id"); // Assuming ID is also selected
                }
            }

            if (leaderboardEntryId != null) {
                // Update existing entry
                String updateLeaderboardSql = "UPDATE LeaderboardEntry SET score = ? WHERE userId = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(updateLeaderboardSql)) {
                    pstmt.setInt(1, currentScore + scoreToAdd);
                    pstmt.setString(2, userId);
                    pstmt.executeUpdate();
                }
            } else {
                // Create new entry
                String insertLeaderboardSql = "INSERT INTO LeaderboardEntry (id, score, userId) VALUES (?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(insertLeaderboardSql)) {
                    pstmt.setString(1, UUID.randomUUID().toString());
                    pstmt.setInt(2, scoreToAdd);
                    pstmt.setString(3, userId);
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Leaderboard updated for " + App.username + ": score added " + scoreToAdd);

        } catch (SQLException e) {
            System.err.println("Database error updating leaderboard: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

    public static void openTakeQuizPage(String quizId, javafx.scene.control.Button currentButton) throws IOException {
        com.quizapp.App.closeCurrentWindow(currentButton); // Close the current window

        TakeQuizAction.currentQuizId = quizId;

        FXMLLoader fxmlLoader = new FXMLLoader(TakeQuizPageController.class.getResource("/com/quizapp/TakeQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setMaximized(true);
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}
