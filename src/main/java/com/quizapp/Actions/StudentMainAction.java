package com.quizapp.Actions;

import com.quizapp.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentMainAction extends App {
    public static void openTakeQuiz() throws IOException {
        com.quizapp.App.changeScene("/com/quizapp/TakeQuiz.fxml");
    }

    public static void openCourses() throws IOException {
        com.quizapp.App.changeScene("/com/quizapp/Courses.fxml");
    }
}