package com.quizapp.Actions;

import com.quizapp.Controllers.LeaderboardPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LeaderboardAction {
    public static void openLeaderBoard() throws IOException {
        com.quizapp.App.changeScene("/com/quizapp/LeaderBoard.fxml");
    }
}