package com.quizapp.Controllers;

import com.quizapp.Actions.QuizListStudentAction;
import com.quizapp.Actions.TakeQuizAction;
import com.quizapp.App;
import com.quizapp.DatabaseUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class QuizListStudentPageController {
    private final QuizListStudentAction quizListStudentAction = new QuizListStudentAction();

    public Button startQuiz;
    @FXML
    public Button leaderBoard;
    @FXML
    public Button enroll;
    public GridPane CourseGrid;
    public ScrollPane scrollPane;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    @FXML
    private Label courseName;

    @FXML
    private Label facultyName;

    @FXML
    private Label courseDescription;

    @FXML
    public void initialize() {
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        // Fetch course details from the database using QuizListStudentAction.currentCourseId
        fetchCourseDetails(QuizListStudentAction.currentCourseId);
        addQuizzesFromMap();
    }

    private void fetchCourseDetails(String courseId) {
        Connection conn = null;
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT c.name, c.description, u.displayName as teacherName FROM Course c JOIN User u ON c.teacherId = u.id WHERE c.id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, courseId);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    courseName.setText(rs.getString("name").replace("_", " "));
                    courseDescription.setText(rs.getString("description"));
                    facultyName.setText("By: " + rs.getString("teacherName"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database error fetching course details: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseUtil.closeConnection(conn);
        }
    }

    private void addQuizzesFromMap() {
        Map<String, String> quizMap = quizListStudentAction.getQuizzesForCourse(QuizListStudentAction.currentCourseId);
        int row = 0;

        for (Map.Entry<String, String> entry : quizMap.entrySet()) {
            String quizId = entry.getKey();
            String title = entry.getValue();

            Label titleLabel = new Label(title);
            titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

            Button takeQuizButton = new Button("Take Quiz");
            takeQuizButton.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-weight: bold;");

            takeQuizButton.setOnAction(e -> {
                try {
                    TakeQuizAction.openTakeQuizPage(quizId, takeQuizButton); // Pass the takeQuizButton
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });

            CourseGrid.add(titleLabel, 0, row);
            CourseGrid.add(takeQuizButton, 1, row);

            row++;
        }
    }
}