package com.quizapp.Controllers;

import com.quizapp.Actions.AddQuizPageAction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddQuizPageController {

    private final AddQuizPageAction addQuizPageAction = new AddQuizPageAction();

    @FXML
    private TextField quizTitleField;
    @FXML
    private GridPane questionGrid;
    @FXML
    private TextField numQuestionsField;
    @FXML
    private TextField quizDurationField;
    @FXML
    private Button saveButton;

    private static String currentCourseName; // To store the name of the currently selected course

    private List<AddQuizPageAction.Question> questions = new ArrayList<>();

    private static final int MAX_QUESTIONS = 10;

    @FXML
    public void initialize() {
        for (int i = 0; i < MAX_QUESTIONS; i++) {
            addNewQuestion(i);
        }

        saveButton.setOnAction(e -> {
            int numQuestions = Integer.parseInt(numQuestionsField.getText().trim());
            int quizDuration = Integer.parseInt(quizDurationField.getText().trim());
            addQuizPageAction.saveQuizToDatabase(currentCourseName, quizTitleField.getText().trim(), numQuestions, quizDuration, questions);
        });
    }

    private void addNewQuestion(int index) {
        TextField questionField = new TextField();
        questionField.setPromptText("Enter question " + (index + 1));
        TextField answerField = new TextField();
        answerField.setPromptText("Enter correct answer");
        TextField option2Field = new TextField();
        option2Field.setPromptText("Enter option 2");
        TextField option3Field = new TextField();
        option3Field.setPromptText("Enter option 3");
        TextField option4Field = new TextField();
        option4Field.setPromptText("Enter option 4");

        questionGrid.add(new Label("Q" + (index + 1)), 0, index * 2);
        questionGrid.add(questionField, 1, index * 2, 5, 1);

        HBox answerOptionsBox = new HBox(10, answerField, option2Field, option3Field, option4Field);
        questionGrid.add(answerOptionsBox, 1, index * 2 + 1, 5, 1);

        AddQuizPageAction.Question question = new AddQuizPageAction.Question("", "", "", "", "");
        questions.add(question);

        questionField.textProperty().addListener((obs, oldVal, newVal) -> question.setText(newVal));
        answerField.textProperty().addListener((obs, oldVal, newVal) -> question.setAnswer(newVal));
        option2Field.textProperty().addListener((obs, oldVal, newVal) -> question.setOption2(newVal));
        option3Field.textProperty().addListener((obs, oldVal, newVal) -> question.setOption3(newVal));
        option4Field.textProperty().addListener((obs, oldVal, newVal) -> question.setOption4(newVal));
    }

    public static void openAddQuizPage(String courseName) throws IOException {
        currentCourseName = courseName;
        com.quizapp.App.changeScene("/com/quizapp/AddQuiz.fxml");
    }
}
