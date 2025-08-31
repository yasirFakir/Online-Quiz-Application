package com.quizapp.Actions;

import com.quizapp.Controllers.LeaderboardPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaderboardAction {
    public static void openLeaderBoard() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LeaderboardPageController.class.getResource("/com/quizapp/LeaderBoard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Leaderboard");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
    }
}