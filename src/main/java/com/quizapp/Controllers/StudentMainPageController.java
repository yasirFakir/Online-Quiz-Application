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
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static com.quizapp.Actions.EnrollAction.openEnrollPage;
import static com.quizapp.Actions.LeaderboardAction.openLeaderBoard;
import static com.quizapp.Actions.QuizListStudentAction.openCourseListStudent;

public class StudentMainPageController {
    @FXML
    private Button enroll;
    @FXML
    private Button leaderBoard;
    @FXML
    private GridPane courseGrid;
    @FXML
    private ImageView logoImage;
    @FXML
    private ImageView userImage;
    @FXML
    private Button logoutButton; // Added logout button
    @FXML
    private Button homeButton; // Added home button
    @FXML
    private Button userButton; // Added user button

    @FXML
    public void initialize(){
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        populateCourses();

        enroll.setOnAction(e -> {
            try {
                openEnrollPage(enroll); // Pass the enroll button
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        leaderBoard.setOnAction(e -> {
            try {
                openLeaderBoard(leaderBoard); // Pass the leaderBoard button
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
                // Reload the current page (StudentMain.fxml)
                com.quizapp.Actions.LoginAction.openStudentMain(homeButton);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        userButton.setOnAction(e -> {
            // Placeholder for user profile action
            System.out.println("User button clicked!");
        });
    }

    private void populateCourses() {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();

            // Get student ID
            String studentId = null;
            String getStudentIdSql = "SELECT id FROM User WHERE username = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(getStudentIdSql)) {
                pstmt.setString(1, App.username);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    studentId = rs.getString("id");
                }
            }

            if (studentId == null) {
                System.err.println("Student not found: " + App.username);
                return;
            }

            String sql = "SELECT c.id, c.name, c.description, u.displayName as teacherName, e.score " +
                         "FROM Enrollment e " +
                         "JOIN Course c ON e.courseId = c.id " +
                         "JOIN User u ON c.teacherId = u.id " +
                         "WHERE e.studentId = ?";

            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, studentId);
                ResultSet rs = pstmt.executeQuery();

                int row = 0;
                int column = 0;

                courseGrid.getChildren().clear();
                courseGrid.setHgap(10);
                courseGrid.setVgap(10);
                courseGrid.setPadding(new Insets(20));
                courseGrid.setAlignment(Pos.TOP_LEFT);

                while (rs.next()) {
                    String courseId = rs.getString("id");
                    String subject = rs.getString("name");
                    String description = rs.getString("description");
                    String faculty = rs.getString("teacherName");
                    int quizTaken = rs.getInt("score"); // Assuming score can represent quizzes taken or progress

                    VBox courseBox = new VBox(20);
                    courseBox.setAlignment(Pos.CENTER);
                    courseBox.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");
                    courseBox.getStyleClass().add("courseGrid");
                    courseBox.setPrefWidth(200);

                    Label subjectLabel = new Label(subject.replace("_", " "));
                    subjectLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                    Label facultyLabel = new Label("By: " + faculty);
                    facultyLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

                    Text descriptionText = new Text(description);
                    descriptionText.setStyle("-fx-font-size: 12px;");
                    descriptionText.setWrappingWidth(300);

                    Label enrolledLabel = new Label("Score: " + quizTaken);
                    enrolledLabel.setStyle("-fx-font-size: 12px;");

                    Button takeQuizButton = new Button("Take Quiz");
                    takeQuizButton.setOnAction(e -> {
                        try {
                            openCourseListStudent(courseId, takeQuizButton); // Pass the takeQuizButton
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });

                    courseBox.getChildren().addAll(subjectLabel, facultyLabel, descriptionText, enrolledLabel, takeQuizButton);

                    courseGrid.add(courseBox, column, row);

                    column++;
                    if (column == 3) {
                        column = 0;
                        row++;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error loading enrolled courses: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }
}
