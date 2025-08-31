package com.quizapp.Controllers;

import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

import static com.quizapp.Actions.QuizListTeacherAction.openCourseListTeacher;

public class TeacherMainPageController {
    public Button addCourses;

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    @FXML
    private GridPane numberGrid;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";
    private String filename = "src/main/resources/teacherInfo/" + App.username + ".csv";

    @FXML
    public void initialize() {
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        addCourses.setOnAction(e -> {
            try {
                AddCourseController.openAddCoursePage();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        currentCourses(filename);
    }

    private void currentCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int row = 0;
            int column = 0;

            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(100));
            gridPane.setAlignment(Pos.TOP_LEFT);

            while ((line = reader.readLine()) != null) {
                String[] courseData = line.split(",");
                String subject = courseData[0];
                String description = courseData[1];
                String enrolled = courseData[2];
                String quizFileName = courseData[3];

                VBox courseBox = new VBox(10);
                courseBox.setAlignment(Pos.CENTER);
                courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                courseBox.setPrefWidth(200);

                Label subjectLabel = new Label(subject);
                subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                Label descriptionLabel = new Label(description);
                descriptionLabel.setStyle("-fx-font-size: 12px;");

                Label enrolledLabel = new Label("Enrolled: " + enrolled);
                enrolledLabel.setStyle("-fx-font-size: 12px;");

                Button checkFilesButton = new Button("Edit");
                checkFilesButton.setOnAction(e -> {
                    try {
                        openCourseListTeacher(quizFileName);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
                courseBox.getChildren().addAll(subjectLabel, descriptionLabel, enrolledLabel, checkFilesButton);

                gridPane.add(courseBox, column, row);

                column++;
                if (column == 4) {
                    column = 0;
                    row++;
                }
            }

            numberGrid.getChildren().clear();
            numberGrid.getChildren().add(gridPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
