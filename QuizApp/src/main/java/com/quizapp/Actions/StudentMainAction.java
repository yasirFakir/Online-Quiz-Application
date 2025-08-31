package com.quizapp.Actions;

import com.quizapp.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentMainAction extends App {
    public static void openTakeQuiz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginAction.class.getResource("/com/quizapp/TakeQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }

    public static void openCourses() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LoginAction.class.getResource("/com/quizapp/Courses.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(StudentMainAction.class.getResource("src/main/resources/css/styles.css").toExternalForm());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("My Courses");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}