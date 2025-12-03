package com.quizapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Stage stg; // Static Stage to control the primary window
    public static String username;
    public static String leaderBoard = "src/main/resources/leaderboard/leader.txt";
    public static final String LOGO_PATH = "/images/logo.png";
    public static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    public static final String BACKGROUND_Main_IMAGE_PATH = "/images/Student-Studying.jpg";

    @Override
    public void start(Stage primaryStage) throws IOException {
        stg = primaryStage; // Assign the primary stage to our static variable
        primaryStage.setResizable(false); // Make the window non-resizable
        
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/quizapp/LoginPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400); // Set initial scene size
        primaryStage.setTitle("Quiz App");
        primaryStage.setMaximized(true);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void changeScene(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load(), stg.getScene().getWidth(), stg.getScene().getHeight()); // Preserve current scene dimensions
        stg.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }

}