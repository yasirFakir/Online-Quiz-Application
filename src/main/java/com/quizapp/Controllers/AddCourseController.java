package com.quizapp.Controllers;

import com.quizapp.Actions.AddCourseAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AddCourseController {

    private final AddCourseAction addCourseAction = new AddCourseAction();

    @FXML
    private TextField courseTitleField;

    @FXML
    private TextArea courseDescriptionField;

    @FXML
    private Button createCourseButton;

    @FXML
    public void createCourse() {
        String courseTitle = courseTitleField.getText().trim();
        String courseDescription = courseDescriptionField.getText().trim();

        boolean success = addCourseAction.createCourse(courseTitle, courseDescription);

        if (success) {
            courseTitleField.clear();
            courseDescriptionField.clear();
        }
    }

    public static void openAddCoursePage() throws IOException {
        com.quizapp.App.changeScene("/com/quizapp/AddCourse.fxml");
    }
}