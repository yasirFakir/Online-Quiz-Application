package com.quizapp.Controllers;

import com.quizapp.Actions.QuizEditPageAction;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class QuizEditPageController {

    private final QuizEditPageAction quizEditPageAction = new QuizEditPageAction();

    @FXML
    private GridPane titleGrid;
    @FXML
    private GridPane questionGrid;
    @FXML
    private Button saveButton;

    private String quizTitle = "";
    private List<QuizEditPageAction.Question> questions;
    private static String filePath;

    @FXML
    public void initialize() {
        if (filePath != null && !filePath.trim().isEmpty()) {
            questions = quizEditPageAction.loadQuizData(filePath);
            displayTitle();
            displayQuestions();
            setupSaveAction();
        } else {
            System.out.println("File path not provided.");
        }
    }

    private void displayTitle() {
        titleGrid.getChildren().clear();
        TextField titleField = new TextField(quizTitle);
        titleField.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        titleField.setPrefWidth(Double.MAX_VALUE);
        titleField.textProperty().addListener((observable, oldValue, newValue) -> quizTitle = newValue);

        titleGrid.add(titleField, 0, 0);
        GridPane.setHgrow(titleField, Priority.ALWAYS);
    }

    private void displayQuestions() {
        questionGrid.getChildren().clear();

        for (int i = 0; i < questions.size(); i++) {
            QuizEditPageAction.Question question = questions.get(i);

            TextField questionField = new TextField(question.getText());
            questionField.setStyle("-fx-font-size: 14px;");
            questionField.setPrefWidth(Double.MAX_VALUE);
            questionField.textProperty().addListener((observable, oldValue, newValue) -> question.setText(newValue));

            HBox questionBox = new HBox(5, questionField);
            HBox.setHgrow(questionField, Priority.ALWAYS);

            questionGrid.add(questionBox, 0, i * 2);

            VBox optionsBox = new VBox(5);
            optionsBox.getChildren().addAll(
                    createEditableOption("Answer: ", question.getAnswer(), question::setAnswer),
                    createEditableOption("Option 2: ", question.getOption2(), question::setOption2),
                    createEditableOption("Option 3: ", question.getOption3(), question::setOption3),
                    createEditableOption("Option 4: ", question.getOption4(), question::setOption4)
            );

            questionGrid.add(optionsBox, 0, i * 2 + 1);
        }
    }

    private HBox createEditableOption(String prefix, String value, QuizEditPageAction.Setter setter) {
        Label label = new Label(prefix);
        TextField optionField = new TextField(value);
        optionField.textProperty().addListener((observable, oldValue, newValue) -> setter.set(newValue));
        return new HBox(5, label, optionField);
    }

    private void setupSaveAction() {
        saveButton.setOnAction(e -> quizEditPageAction.saveQuizToFile(filePath, quizTitle, questions));
    }

    public static void openEditQuizPage(String file) throws IOException {
        filePath = file;
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/quizapp/QuizEdit.fxml"));
        Stage stage = new Stage();
        stage.setScene(new Scene(loader.load()));
        stage.setTitle("Edit Quiz");
        stage.setMaximized(true);
        stage.show();
    }
}