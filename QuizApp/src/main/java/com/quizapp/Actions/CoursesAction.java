package com.quizapp.Actions;

import java.io.IOException;

public class CoursesAction {
    public void goHome() throws IOException {
        new LoginAction().openTeacherMain();
    }
}