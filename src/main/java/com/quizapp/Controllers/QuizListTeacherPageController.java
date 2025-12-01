package com.quizapp.Controllers;

import com.quizapp.Actions.QuizListTeacherAction;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class QuizListTeacherPageController {
    private final QuizListTeacherAction quizListTeacherAction = new QuizListTeacherAction();

    public Button addQuiz;
    public GridPane CourseGrid;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    @FXML
    public void initialize() {
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        addQuiz.setOnAction(e -> {
            try {
                AddQuizPageController.openAddQuizPage(QuizListTeacherAction.currentCourseId, addQuiz); // Pass the addQuiz button
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        addQuizzesFromMap();
    }

    private void addQuizzesFromMap() {
        CourseGrid.getChildren().clear();
        Map<String, String> quizMap = quizListTeacherAction.getQuizzesForTeacherCourse(QuizListTeacherAction.currentCourseId);
        int row = 0;

        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            String quizId = entry.getKey();
            String title = entry.getValue();

            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

            Button editButton = new Button("Edit");
            editButton.setStyle("-fx-background-color: #90ee90; -fx-text-fill: white; -fx-font-weight: bold;");

            editButton.setOnAction(e -> {
                try {
                    QuizEditPageController.openEditQuizPage(quizId, editButton); // Pass the editButton
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            CourseGrid.add(titleLabel, 0, row);
            CourseGrid.add(editButton, 1, row);

            row++;
        }
    }
}