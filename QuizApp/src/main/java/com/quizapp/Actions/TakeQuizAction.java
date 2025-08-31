package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.Controllers.TakeQuizPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TakeQuizAction {
    public static String filePath;
    public static String courseName;

    public void updateLeaderFile(int scoreToAdd) throws IOException {
        List<String[]> leaders = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(App.leaderBoard))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                leaders.add(data);
            }
        }

        boolean userFound = false;
        for (String[] leader : leaders) {
            if (leader[1].equals(App.username)) {
                int currentScore = Integer.parseInt(leader[0].trim());
                leader[0] = String.valueOf(currentScore + scoreToAdd);
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            leaders.add(new String[]{String.valueOf(scoreToAdd), App.username});
        }

        leaders.sort((a, b) -> Integer.parseInt(b[0]) - Integer.parseInt(a[0]));

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(App.leaderBoard))) {
            for (String[] leader : leaders) {
                bw.write(String.join(" ", leader));
                bw.newLine();
            }
        }
    }

    public void updateQuizTakenCount(String courseName) {
        String file = "src/main/resources/studentInfo/" + App.username + ".csv";
        List<String[]> rows = new ArrayList<>();

        try {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    rows.add(line.split(","));
                }
            }

            boolean courseFound = false;
            for (String[] row : rows) {
                if (row[0].equals(courseName)) {
                    int quizTakenCount = Integer.parseInt(row[2].trim());
                    quizTakenCount++;
                    row[2] = String.valueOf(quizTakenCount);
                    courseFound = true;
                    break;
                }
            }

            if (!courseFound) {
                return;
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (String[] row : rows) {
                    String line = String.join(",", row);
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void openTakeQuizPage(String quizDir, String fileName) throws IOException {
        filePath = quizDir + fileName;
        courseName = quizDir.endsWith("/") ? quizDir.substring(0, quizDir.length() - 1) : quizDir;
        courseName = courseName.substring(courseName.lastIndexOf("/") + 1);

        FXMLLoader fxmlLoader = new FXMLLoader(TakeQuizPageController.class.getResource("/com/quizapp/TakeQuiz.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage takeQuizStage = new Stage();
        takeQuizStage.setTitle("Take Quiz");
        takeQuizStage.setMaximized(true);
        takeQuizStage.setScene(scene);
        takeQuizStage.show();
    }
}