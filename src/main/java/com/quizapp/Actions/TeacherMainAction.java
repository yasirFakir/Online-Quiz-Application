package com.quizapp.Actions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherMainAction {
    public static void openAddCourses() throws IOException {
        com.quizapp.App.changeScene("/com/quizapp/Courses.fxml");
    }
}