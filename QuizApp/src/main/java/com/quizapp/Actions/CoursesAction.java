package com.quizapp.Actions;

import java.io.IOException;

public class CoursesAction {
    public void goHome(javafx.scene.control.Button currentButton) throws IOException {
        new LoginAction().openTeacherMain(currentButton);
    }
}