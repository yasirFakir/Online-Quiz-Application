package com.quizapp.Controllers;

import com.quizapp.Actions.EnrollAction;
import com.quizapp.Actions.LeaderboardAction;
import com.quizapp.Actions.LoginAction;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class EnrollPageController {

    private final EnrollAction enrollAction = new EnrollAction();
    private final LoginAction loginAction = new LoginAction();

    @FXML
    private Button home;
    @FXML
    private Button leaderBoard;
    @FXML
    private GridPane courseGrid;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String USER_IMAGE_PATH = "/images/temp.jpg";
    private static final String COURSES_FILE = "/Courses/all.csv";

    @FXML
    private void initialize() {
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(LOGO_PATH)).toExternalForm()));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(USER_IMAGE_PATH)).toExternalForm()));
        } catch (Exception e) {
            System.err.println("Error loading images: " + e.getMessage());
        }

        leaderBoard.setOnAction(e -> {
            try {
                LeaderboardAction.openLeaderBoard();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        home.setOnAction(e -> {
            try {
                loginAction.openStudentMain();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        populateCourses(COURSES_FILE);
    }

    private void populateCourses(String fileName) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Objects.requireNonNull(getClass().getResourceAsStream(fileName))))) {

            String line;
            int row = 0;
            int column = 0;

            courseGrid.getChildren().clear();
            courseGrid.setHgap(10);
            courseGrid.setVgap(10);
            courseGrid.setPadding(new Insets(20));
            courseGrid.setAlignment(Pos.TOP_LEFT);

            while ((line = reader.readLine()) != null) {
                String[] courseData = line.split(",");
                String courseName = courseData[0].trim().replace("_", " ");
                String description = courseData[1].trim();

                VBox courseBox = createCourseBox(courseName, description);
                courseGrid.add(courseBox, column, row);

                column++;
                if (column == 3) {
                    column = 0;
                    row++;
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading courses: " + e.getMessage());
        }
    }

    private VBox createCourseBox(String courseName, String description) {
        VBox courseBox = new VBox(10);
        courseBox.setAlignment(Pos.CENTER);
        courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
        courseBox.setPrefWidth(200);

        Label nameLabel = new Label(courseName);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label descriptionLabel = new Label(description);
        descriptionLabel.setWrapText(true);
        descriptionLabel.setStyle("-fx-font-size: 12px;");

        Button enrollButton = new Button("Enroll");
        enrollButton.setOnAction(e -> enrollAction.enrollCourse(courseName, description));

        courseBox.getChildren().addAll(nameLabel, descriptionLabel, enrollButton);
        return courseBox;
    }
}
