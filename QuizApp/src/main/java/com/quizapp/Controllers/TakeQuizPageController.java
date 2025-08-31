package com.quizapp.Controllers;

import com.quizapp.Actions.TakeQuizAction;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        loadQuestionsFromDatabase();
        displayQuestions();
        setupSubmitAction();
    }

    private void loadQuestionsFromDatabase() {
        String sql = "SELECT text, correctAnswer, options FROM Question WHERE quizId = ?";

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, TakeQuizAction.currentQuizId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String text = rs.getString("text");
                String correctAnswer = rs.getString("correctAnswer");
                String optionsString = rs.getString("options");

                List<String> options = new ArrayList<>();
                options.add(correctAnswer); // Add correct answer first
                options.addAll(Arrays.asList(optionsString.split(","))); // Add other options

                Collections.shuffle(options);

                questions.add(new Question(text, correctAnswer, options));
            }
        } catch (SQLException e) {
            System.err.println("Database error loading questions: " + e.getMessage());
            e.printStackTrace();
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

            // Update leaderboard
            takeQuizAction.updateLeaderboardScore((int) score);
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
