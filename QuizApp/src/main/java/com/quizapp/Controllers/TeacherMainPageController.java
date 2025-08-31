package com.quizapp.Controllers;

import com.quizapp.App;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.quizapp.Actions.QuizListTeacherAction.openCourseListTeacher;

public class TeacherMainPageController {
    public Button addCourses;
    @FXML
    private Button logoutButton; // Added logout button

    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;
    @FXML
    private Button homeButton; // Added home button
    @FXML
    private Button userButton; // Added user button

    @FXML
    private GridPane numberGrid;

    private static final String LOGO_PATH = "/images/logo.png";
    private static final String BACKGROUND_IMAGE_PATH = "/images/temp.jpg";

    @FXML
    public void initialize() {
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        addCourses.setOnAction(e -> {
            try {
                AddCourseController.openAddCoursePage(addCourses); // Pass the addCourses button
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        logoutButton.setOnAction(e -> {
            try {
                com.quizapp.Actions.LoginAction.logout(logoutButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        homeButton.setOnAction(e -> {
            try {
                // Reload the current page (TeacherMain.fxml)
                com.quizapp.Actions.LoginAction.openTeacherMain(homeButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        userButton.setOnAction(e -> {
            // Placeholder for user profile action
            System.out.println("User button clicked!");
        });

        currentCourses();
    }

    private void currentCourses() {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();

            // Get teacher ID
            String teacherId = null;
            String getTeacherIdSql = "SELECT id FROM User WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(getTeacherIdSql)) {
                pstmt.setString(1, App.username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    teacherId = rs.getString("id");
                }
            }

            if (teacherId == null) {
                System.err.println("Teacher not found: " + App.username);
                return;
            }

            String sql = "SELECT id, name, description FROM Course WHERE teacherId = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, teacherId);
                ResultSet rs = pstmt.executeQuery();

                int row = 0;
                int column = 0;

                GridPane gridPane = new GridPane();
                gridPane.setHgap(10);
                gridPane.setVgap(10);
                gridPane.setPadding(new Insets(100));
                gridPane.setAlignment(Pos.TOP_LEFT);

                while (rs.next()) {
                    String courseId = rs.getString("id");
                    String subject = rs.getString("name");
                    String description = rs.getString("description");

                    // You might want to query the number of enrolled students for this course
                    // or the number of quizzes, if that's what 'enrolled' represented.
                    // For now, we'll just display a placeholder or omit it if not directly available.
                    // int enrolledCount = getEnrolledStudentCount(courseId); // Example

                    VBox courseBox = new VBox(10);
                    courseBox.setAlignment(Pos.CENTER);
                    courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                    courseBox.setPrefWidth(200);

                    Label subjectLabel = new Label(subject.replace("_", " "));
                    subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                    Label descriptionLabel = new Label(description);
                    descriptionLabel.setStyle("-fx-font-size: 12px;");

                    // Label enrolledLabel = new Label("Enrolled: " + enrolledCount); // Example
                    // enrolledLabel.setStyle("-fx-font-size: 12px;");

                    Button checkFilesButton = new Button("Edit");
                    checkFilesButton.setOnAction(e -> {
                        try {
                            openCourseListTeacher(courseId, checkFilesButton); // Pass the checkFilesButton
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });

                    Button addQuizButton = new Button("Add Quiz");
                    addQuizButton.setOnAction(e -> {
                        try {
                            AddQuizPageController.openAddQuizPage(subject, addQuizButton); // Pass the course name and addQuizButton
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });

                    courseBox.getChildren().addAll(subjectLabel, descriptionLabel, checkFilesButton, addQuizButton);

                    gridPane.add(courseBox, column, row);

                    column++;
                    if (column == 4) {
                        column = 0;
                        row++;
                    }
                }

                numberGrid.getChildren().clear();
                numberGrid.getChildren().add(gridPane);
            }
        } catch (SQLException e) {
            System.err.println("Database error loading teacher courses: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

    // Example method to get enrolled student count (can be implemented if needed)
    /*
    private int getEnrolledStudentCount(String courseId) {
        String sql = "SELECT COUNT(*) FROM Enrollment WHERE courseId = ?";
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error getting enrolled student count: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    */
}