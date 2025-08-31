package com.quizapp.Actions;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class QuizListTeacherAction {
    public static String quizDir = "src/main/resources/Courses/";
    public static String quizFile = "";

    public Map<String, String> courseList(String commonPrefix) {
        Map<String, String> quizMap = new HashMap<>();
        String fullQuizDir = quizDir + quizFile;
        File directory = new File(fullQuizDir);
        if (!directory.isDirectory()) {
            return quizMap;
        }

        File[] matchingFiles = directory.listFiles((dir, name) -> name.startsWith(commonPrefix));
        if (matchingFiles == null) {
            return quizMap;
        }

        for (File file : matchingFiles) {
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

    public static void openCourseListTeacher(String quizFileName) throws IOException {
        quizFile = quizFileName;
        FXMLLoader fxmlLoader = new FXMLLoader(QuizListTeacherAction.class.getResource("/com/quizapp/QuizListTeacher.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setMaximized(true);
        takeQuizStage.setTitle("Course List");
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}