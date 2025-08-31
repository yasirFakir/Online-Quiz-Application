package com.quizapp.Actions;

import javafx.scene.control.Alert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AddCourseAction {

    private static final String COURSES_DIRECTORY = "src/main/resources/Courses/";

    public boolean createCourse(String courseTitle, String courseDescription) {
        if (courseTitle.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Course title is required.");
            return false;
        }

        courseTitle = courseTitle.replace(" ", "_");
        Path courseDirectory = Paths.get(COURSES_DIRECTORY, courseTitle);

        try {
            if (Files.exists(courseDirectory)) {
                showAlert(Alert.AlertType.ERROR, "Error", "A course with this title already exists.");
                return false;
            }

            Files.createDirectories(courseDirectory);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Course created successfully.");
            return true;
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to create the course directory: " + e.getMessage());
            return false;
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
