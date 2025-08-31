package com.quizapp.Actions;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AddQuizPageAction {

    public void saveQuizToFile(String quizDir, String title, List<Question> questions) {
        if (title.isEmpty()) {
            showErrorMessage("Quiz title is required.");
            return;
        }

        boolean hasValidQuestion = questions.stream()
                .anyMatch(q -> !q.getText().isEmpty() && !q.getAnswer().isEmpty());

        if (!hasValidQuestion) {
            showErrorMessage("At least one question with a correct answer must be filled.");
            return;
        }

        File directory = new File(quizDir);
        if (!directory.exists() && !directory.mkdirs()) {
            showErrorMessage("Could not create directory for quizzes.");
            return;
        }

        int nextQuizNumber = 1;
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                if (name.matches("quiz\\d+\\.csv")) {
                    int currentNumber = Integer.parseInt(name.replaceAll("\\D", ""));
                    nextQuizNumber = Math.max(nextQuizNumber, currentNumber + 1);
                }
            }
        }
        String fileName = "quiz" + nextQuizNumber + ".csv";
        File file = new File(directory, fileName);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(title);
            writer.newLine();

            for (Question question : questions) {
                if (!question.getText().isEmpty() && !question.getAnswer().isEmpty()) {
                    writer.write(String.format("%s,%s,%s,%s,%s",
                            question.getText(),
                            question.getAnswer(),
                            question.getOption2(),
                            question.getOption3(),
                            question.getOption4()));
                    writer.newLine();
                }
            }

            showSuccessMessage("Quiz saved successfully as: " + file.getName());
        } catch (IOException e) {
            showErrorMessage("Error saving quiz: " + e.getMessage());
        }
    }

    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    private void showSuccessMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
    }

    public static class Question {
        private String text;
        private String answer;
        private String option2;
        private String option3;
        private String option4;

        public Question(String text, String answer, String option2, String option3, String option4) {
            this.text = text;
            this.answer = answer;
            this.option2 = option2;
            this.option3 = option3;
            this.option4 = option4;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public String getOption2() {
            return option2;
        }

        public void setOption2(String option2) {
            this.option2 = option2;
        }

        public String getOption3() {
            return option3;
        }

        public void setOption3(String option3) {
            this.option3 = option3;
        }

        public String getOption4() {
            return option4;
        }

        public void setOption4(String option4) {
            this.option4 = option4;
        }
    }
}
