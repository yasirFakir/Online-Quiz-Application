package com.quizapp.Actions;

import com.quizapp.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuizListStudentAction {

    public static String subject;
    public static String faculty;
    public static String description;
    public static String quizDir;

    public Map<String, String> courseList() {
        Map<String, String> quizMap = new HashMap<>();
        File directory = new File(quizDir);
        if (!directory.exists() || !directory.isDirectory()) {
            return quizMap;
        }

        File[] allFiles = directory.listFiles();
        if (allFiles == null) {
            return quizMap;
        }

        for (File file : allFiles) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String title = reader.readLine();
                if (title == null || title.trim().isEmpty()) {
                    title = "Untitled Quiz";
                }
                quizMap.put(file.getName(), title);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return quizMap;
    }

    public static void openCourseListStudent(String quizFileName, String subject, String faculty, String description) throws IOException {
        QuizListStudentAction.subject = subject;
        QuizListStudentAction.faculty = faculty;
        QuizListStudentAction.description = description;
        QuizListStudentAction.quizDir = "src/main/resources/Courses/" + quizFileName + "/";

        FXMLLoader fxmlLoader = new FXMLLoader(QuizListStudentAction.class.getResource("/com/quizapp/QuizListStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Quiz List");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}