package com.quizapp.Actions;

import com.quizapp.App;
import com.quizapp.Controllers.EnrollPageController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class EnrollAction {

    public void enrollCourse(String courseFileName, String courseInfo) {
        boolean isCourseFound = false;

        String userFilePath = "src/main/resources/studentInfo/" + App.username + ".csv";

        try (BufferedReader br = new BufferedReader(new FileReader(userFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] courseDetails = line.split(",", 2);
                if (courseDetails.length > 0 && courseDetails[0].trim().equalsIgnoreCase(courseFileName.replace(" ", "_"))) {
                    isCourseFound = true;
                    showAlert("Already Enrolled", "You are already enrolled in: " + courseFileName);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("User file not found: " + userFilePath + ". Assuming no prior enrollment.");
        } catch (IOException e) {
            System.err.println("Error reading user file: " + e.getMessage());
            return;
        }

        if (!isCourseFound) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(userFilePath, true))) {
                int progress = 0;
                bw.write(courseFileName.replace(" ", "_") + "," + courseInfo + "," + progress);
                bw.newLine();
                System.out.println("Enrolled successfully in course: " + courseFileName);
                updateCourseEnrollmentCount(courseFileName.replace(" ", "_"));
                showAlert("Enrollment Successful", "You have successfully enrolled in: " + courseFileName);
            } catch (IOException e) {
                System.err.println("Error writing to user file: " + e.getMessage());
            }
        }
    }

    private void updateCourseEnrollmentCount(String courseFileName) {
        String faculty;
        String[] parts = courseFileName.split("_by_");
        faculty = parts[1];

        String filePath = "src/main/resources/teacherInfo/" + faculty.trim() + ".csv";
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] courseDetails = line.split(",", 4);

                if (courseDetails.length == 4) {
                    String fileName = courseDetails[3].trim();
                    if (fileName.equalsIgnoreCase(courseFileName)) {
                        int enrolledCount = Integer.parseInt(courseDetails[2].trim());
                        enrolledCount++;
                        line = courseDetails[0] + "," + courseDetails[1] + "," + enrolledCount + "," + fileName;
                    }
                }

                updatedContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + filePath + ". Message: " + e.getMessage());
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(updatedContent.toString());
            System.out.println("Updated enrolled count for course: " + courseFileName + " in file: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + filePath + ". Message: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void openEnrollPage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(EnrollPageController.class.getResource("/com/quizapp/Enroll.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(EnrollPageController.class.getResource("/css/style.css")).toExternalForm());

        Stage enrollStage = new Stage();
        enrollStage.setTitle("Enroll in a Course");
        enrollStage.setMaximized(true);
        enrollStage.setScene(scene);
        enrollStage.show();
    }
}