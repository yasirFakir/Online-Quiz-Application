package com.quizapp.Controllers;

import com.quizapp.Actions.CoursesAction;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.util.Objects;

public class CoursesController {
    private final CoursesAction coursesAction = new CoursesAction();

    public Button Home;
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView userImage;

    public void initialize(){
        try {
            logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));
            userImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_IMAGE_PATH))));
        } catch (NullPointerException e) {
            System.err.println("Resource not found: " + e.getMessage());
        }

        Home.setOnAction(e -> {
            try {
                coursesAction.goHome(Home); // Pass the Home button
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}