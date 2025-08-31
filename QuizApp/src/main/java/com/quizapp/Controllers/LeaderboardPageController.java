package com.quizapp.Controllers;

import com.quizapp.Actions.EnrollAction;
import com.quizapp.Actions.LoginAction;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    private static final String LEADERBOARD_FILE = "src/main/resources/leaderboard/leader.txt";

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
                loginAction.openStudentMain();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        enroll.setOnAction(e -> {
            try {
                EnrollAction.openEnrollPage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private List<Player> loadLeaderboardData() {
        List<Player> players = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(LEADERBOARD_FILE))) {
            String line;
            int rank = 1;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ", 2);
                if (parts.length == 2) {
                    int score = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    players.add(new Player(rank++, name, score));
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading leaderboard data: " + e.getMessage());
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
