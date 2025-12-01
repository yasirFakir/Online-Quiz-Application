package com.quizapp.Controllers;

import com.quizapp.Actions.LoginAction;
import com.quizapp.App;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class LoginPageController {

    private final LoginAction loginAction = new LoginAction();

    // FXML Components
    @FXML
    private ImageView logoImage;

    @FXML
    private ImageView mainImage;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label lblMessage;

    @FXML
    private Button signUpButton;


    @FXML
    public void initialize() {
        // Initialize the logo
        logoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.LOGO_PATH))));

        // Initialize the main image
        Image mainBgImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(App.BACKGROUND_Main_IMAGE_PATH)));
        mainImage.setImage(mainBgImage);


        // Set up key event handlers to move focus to the next field on ENTER key press
        usernameField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                passwordField.requestFocus();
            }
        });

        passwordField.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                loginButton.requestFocus();
                loginButton.fire();
            }
        });

        // Add functionality to buttons (Sign Up and Login)
        signUpButton.setOnAction(e -> {
            try {
                handleSignUp();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginButton.setOnAction(e -> {
            try {
                handleLogin();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void handleSignUp() throws IOException {
        // Handle sign-up logic here
        loginAction.openSignUpWindow(signUpButton); // Pass the signUpButton
        lblMessage.setText("Sign up button clicked!");
    }

    private void handleLogin() throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        loginAction.handleLoginAction(username,password,lblMessage,loginButton);  // Call the method to set up the actions
    }

    
}