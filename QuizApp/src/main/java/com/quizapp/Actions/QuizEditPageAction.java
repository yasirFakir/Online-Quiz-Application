package com.quizapp.Actions;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class QuizEditPageAction {

    public List<Question> loadQuizData(String filePath) {
        List<Question> questions = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) {
            return questions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip title
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 5);
                if (parts.length < 5) continue;

                questions.add(new Question(parts[0], parts[1], parts[2], parts[3], parts[4]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public void saveQuizToFile(String filePath, String quizTitle, List<Question> questions) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(quizTitle);
            writer.newLine();

            for (Question question : questions) {
                writer.write(String.format("%s,%s,%s,%s,%s",
                        question.getText(),
                        question.getAnswer(),
                        question.getOption2(),
                        question.getOption3(),
                        question.getOption4()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FunctionalInterface
    public interface Setter {
        void set(String value);
    }
}
