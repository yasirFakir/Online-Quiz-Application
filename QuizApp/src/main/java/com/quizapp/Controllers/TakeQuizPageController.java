package com.quizapp.Controllers;

import com.quizapp.Actions.TakeQuizAction;
import com.quizapp.DatabaseUtil;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

import java.io.IOException; // Added for handling navigation exceptions

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
    private Label timerLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Button homeButton; // Added home button

    private int quizDurationMinutes;
    private Timeline timeline;

    private List<Question> questions = new ArrayList<>();
    private Map<Integer, ToggleGroup> toggleGroups = new HashMap<>();

    @FXML
    public void initialize() {
        loadQuizDetails(); // Load quiz duration
        loadQuestionsFromDatabase();
        displayQuestions();
        setupSubmitAction();
        startTimer(); // Start the timer after loading quiz details

        homeButton.setOnAction(e -> {
            try {
                // Stop the timer if the user navigates away
                if (timeline != null) {
                    timeline.stop();
                }
                com.quizapp.Actions.LoginAction.openStudentMain(homeButton); // Navigate to student main page
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
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

    private void loadQuizDetails() {
        String sql = "SELECT quizDuration FROM Quiz WHERE id = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, TakeQuizAction.currentQuizId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                quizDurationMinutes = rs.getInt("quizDuration");
            } else {
                System.err.println("Quiz duration not found for quizId: " + TakeQuizAction.currentQuizId);
                quizDurationMinutes = 0; // Default to 0 if not found
            }
        } catch (SQLException e) {
            System.err.println("Database error loading quiz duration: " + e.getMessage());
            e.printStackTrace();
            quizDurationMinutes = 0; // Default to 0 on error
        }
    }

    private void startTimer() {
        final int[] seconds = {quizDurationMinutes * 60}; // Convert minutes to seconds

        timerLabel.setText(String.format("Time: %02d:%02d", seconds[0] / 60, seconds[0] % 60));

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            seconds[0]--;
            if (seconds[0] >= 0) {
                timerLabel.setText(String.format("Time: %02d:%02d", seconds[0] / 60, seconds[0] % 60));
            } else {
                timeline.stop();
                // Auto-submit quiz when timer runs out
                submitButton.fire(); // Simulate button click
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Time's Up!");
                alert.setHeaderText(null);
                alert.setContentText("Your time for the quiz has run out. Your answers have been submitted.");
                alert.showAndWait();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
