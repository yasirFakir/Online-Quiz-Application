package com.quizapp.Controllers;

import com.quizapp.Actions.EnrollAction;
import com.quizapp.Actions.LoginAction;
import com.quizapp.App;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaderboardPageController {
    private final LoginAction loginAction = new LoginAction();

    @FXML
    private Button home;
    @FXML
    private Button enroll;
    @FXML
    private TableView<Player> leaderboardTable;

    @FXML
    private TableColumn<Player, Integer> rankColumn;

    @FXML
    private TableColumn<Player, String> nameColumn;

    @FXML
    private TableColumn<Player, Integer> scoreColumn;

    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;

    @FXML
    private void initialize() {

        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        rankColumn.setCellValueFactory(new PropertyValueFactory<>("rank"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));

        leaderboardTable.getItems().setAll(loadLeaderboardData());

        home.setOnAction(e -> {
            try {
                loginAction.openStudentMain(home); // Pass the home button
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        enroll.setOnAction(e -> {
            try {
                EnrollAction.openEnrollPage(enroll); // Pass the enroll button
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private List<Player> loadLeaderboardData() {
        List<Player> players = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT u.username, l.score FROM LeaderboardEntry l JOIN User u ON l.userId = u.id ORDER BY l.score DESC";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                ResultSet rs = pstmt.executeQuery();
                int rank = 1;
                while (rs.next()) {
                    String username = rs.getString("username");
                    int score = rs.getInt("score");
                    players.add(new Player(rank++, username, score));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error loading leaderboard data: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
        return players;
    }

    public static class Player {
        private final int rank;
        private final String name;
        private final int score;

        public Player(int rank, String name, int score) {
            this.rank = rank;
            this.name = name;
            this.score = score;
        }

        public int getRank() {
            return rank;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }
    }
}