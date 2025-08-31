package com.quizapp.Actions;

import com.quizapp.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SignUpAction {

    public boolean handleSignUpAction(String fullName, String nickName, String username, String password, String confirmPassword, String role) {
        if (!password.equals(confirmPassword)) {
            return false; // Passwords do not match
        }

        boolean isSaved = saveUserCredentials(username, password, fullName, nickName, role);
        if (isSaved) {
            saveUserFile(role, username);
            if ("Student".equals(role)) {
                String leaderFilePath = "src/main/resources/leaderboard/leader.txt";
                String contentToAppend = "0," + username;

                try (FileWriter writer = new FileWriter(leaderFilePath, true)) { // Open in append mode
                    writer.append(contentToAppend).append("\n"); // Append the content followed by a new line
                } catch (IOException e) {
                    e.printStackTrace(); // Log the error for debugging
                }
            }
        }
        return isSaved;
    }

    private void createFileIfNotExists(String directoryPath, String fileName) throws IOException {
        File directory = new File(directoryPath);
        File file = new File(directoryPath, fileName);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        if (!file.exists()) {
            file.createNewFile();
        }
    }

    private boolean saveUserFile(String role, String username) {
        String userDirectory = role.equals("Teacher")
                ? "src/main/resources/teacherInfo/"
                : "src/main/resources/studentInfo/";
        String userFilePath = userDirectory + username + ".csv";

        try {
            createFileIfNotExists(userDirectory, username + ".csv");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean saveUserCredentials(String username, String password, String fullName, String nickName, String role) {
        String fileName = role.equals("Teacher") ? "teacher.csv" : "student.csv";
        String filePath = "src/main/resources/credential/" + fileName;
        File directory = new File("src/main/resources/credential");

        try {
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File csvFile = new File(directory, fileName);
            if (!csvFile.exists()) {
                csvFile.createNewFile();
            }

            try (FileWriter writer = new FileWriter(csvFile, true)) {
                writer.append(escapeForCSV(username)).append(',')
                        .append(escapeForCSV(password)).append(',')
                        .append(escapeForCSV(fullName)).append(',')
                        .append(escapeForCSV(nickName)).append(',')
                        .append(escapeForCSV(role)).append('\n');
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void start() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SignUpAction.class.getResource("/com/quizapp/LoginPageUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setMaximized(true);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    private String escapeForCSV(String value) {
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            value = "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}