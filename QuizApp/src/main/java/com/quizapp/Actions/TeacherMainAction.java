package com.quizapp.Actions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherMainAction {
    public static void openAddCourses() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherMainAction.class.getResource("/com/quizapp/Courses.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Add Courses");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}