package com.quizapp.Controllers;

import com.quizapp.Actions.SignUpAction;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SignUpPageController {

    private final SignUpAction signUpAction = new SignUpAction();

    public ImageView mainImage;
    @FXML
    private ImageView logoImage;

    @FXML
    private Button signInButton, signUpButton;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField fullNameField, nickNameField, usernameField;

    @FXML
    private PasswordField passwordField, confirmPasswordField;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        logoImage.setImage(new Image(getClass().getResourceAsStream(App.LOGO_PATH)));

        Image mainBgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_Main_IMAGE_PATH)));
        mainImage.setImage(mainBgImage);

        roleComboBox.getItems().addAll("Teacher", "Student");

        fullNameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                nickNameField.requestFocus();
            }
        });

        nickNameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                usernameField.requestFocus();
            }
        });

        usernameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                confirmPasswordField.requestFocus();
            }
        });

        confirmPasswordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                signUpButton.requestFocus();
            }
        });

        signUpButton.setOnAction(event -> handleSignUp());

        signInButton.setOnAction(e -> {
            try {
                SignUpAction.start();
                closeCurrentWindow();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleSignUp() {
        String role = roleComboBox.getValue();
        String fullName = fullNameField.getText();
        String nickName = nickNameField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (fullName.isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Please enter your full name.");
            fullNameField.requestFocus();
            return;
        }

        if (nickName.isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Please enter your nickname.");
            nickNameField.requestFocus();
            return;
        }

        if (username.isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Please enter a username.");
            usernameField.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Please enter a password.");
            passwordField.requestFocus();
            return;
        }

        if (confirmPassword.isEmpty()) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Please confirm your password.");
            confirmPasswordField.requestFocus();
            return;
        }

        if (role == null) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Please select a role.");
            roleComboBox.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("Passwords do not match.");
            confirmPasswordField.requestFocus();
            return;
        }

        boolean isSignedUp = signUpAction.handleSignUpAction(fullName, nickName, username, password, confirmPassword, role);

        if (isSignedUp) {
            messageLabel.setTextFill(Color.GREEN);
            messageLabel.setText("Sign-up successful! You can now log in.");
        } else {
            messageLabel.setTextFill(Color.RED);
            messageLabel.setText("An error occurred. Please try again.");
        }
    }

    public void closeCurrentWindow() {
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.close();
    }
}