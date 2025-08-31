package com.quizapp.Controllers;

import com.quizapp.Actions.TakeQuizAction;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class TakeQuizPageController {

    private final TakeQuizAction takeQuizAction = new TakeQuizAction();

    @FXML
    private GridPane questionGrid;
    @FXML
    private Button submitButton;

    private List<Question> questions = new ArrayList<>();
    private Map<Integer, ToggleGroup> toggleGroups = new HashMap<>();

    @FXML
    public void initialize() {
        try {
            loadQuestionsFromFile();
            displayQuestions();
            setupSubmitAction();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestionsFromFile() throws IOException {
        File file = new File(TakeQuizAction.filePath);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip title
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length < 5) continue;

                String text = parts[0];
                String correctAnswer = parts[1];
                List<String> options = Arrays.asList(parts[1], parts[2], parts[3], parts[4]);

                Collections.shuffle(options);

                questions.add(new Question(text, correctAnswer, options));
            }
        }
    }

    private void displayQuestions() {
        questionGrid.getChildren().clear();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);

            Label questionLabel = new Label((i + 1) + ". " + question.getText());
            questionGrid.add(questionLabel, 0, i * 2);

            ToggleGroup group = new ToggleGroup();
            toggleGroups.put(i, group);

            HBox optionsBox = new HBox(10);
            for (String option : question.getOptions()) {
                RadioButton optionButton = new RadioButton(option);
                optionButton.setToggleGroup(group);
                optionsBox.getChildren().add(optionButton);
            }

            questionGrid.add(optionsBox, 0, i * 2 + 1, 2, 1);
        }
    }

    private void setupSubmitAction() {
        submitButton.setOnAction(e -> {
            int correctCount = 0;

            for (int i = 0; i < questions.size(); i++) {
                ToggleGroup group = toggleGroups.get(i);
                if (group != null && group.getSelectedToggle() != null) {
                    RadioButton selected = (RadioButton) group.getSelectedToggle();
                    if (selected.getText().equals(questions.get(i).getCorrectAnswer())) {
                        correctCount++;
                    }
                }
            }

            double score = ((double) correctCount / questions.size()) * 100;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Results");
            alert.setHeaderText("Quiz Completed!");
            alert.setContentText("You scored: " + String.format("%.2f", score) + " out of 100");
            alert.showAndWait();

            takeQuizAction.updateQuizTakenCount(TakeQuizAction.courseName);

            try {
                takeQuizAction.updateLeaderFile((int) score);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private static class Question {
        private final String text;
        private final String correctAnswer;
        private final List<String> options;

        public Question(String text, String correctAnswer, List<String> options) {
            this.text = text;
            this.correctAnswer = correctAnswer;
            this.options = options;
        }

        public String getText() {
            return text;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public List<String> getOptions() {
            return options;
        }
    }
}