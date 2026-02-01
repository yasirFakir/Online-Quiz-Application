package com.quiz.ui

import androidx.compose.runtime.*
import com.quiz.ui.screens.auth.LoginScreen
import com.quiz.ui.screens.auth.SignupScreen
import com.quiz.ui.screens.student.StudentDashboard
import com.quiz.ui.screens.teacher.TeacherDashboard
import com.quiz.ui.theme.GoniaEduTheme

sealed class Screen {
    object Login : Screen()
    object Signup : Screen()
    data class StudentDashboard(val username: String) : Screen()
    data class TeacherDashboard(val username: String) : Screen()
}

@Composable
fun App() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) }

    GoniaEduTheme {
        when (val screen = currentScreen) {
            is Screen.Login -> LoginScreen(
                onLoginSuccess = { role ->
                    currentScreen = if (role == "TEACHER") {
                        Screen.TeacherDashboard("Prof. Teacher")
                    } else {
                        Screen.StudentDashboard("Student User")
                    }
                },
                onNavigateToSignup = { currentScreen = Screen.Signup }
            )
            is Screen.Signup -> SignupScreen(
                onSignupSuccess = { currentScreen = Screen.Login },
                onNavigateToLogin = { currentScreen = Screen.Login }
            )
            is Screen.StudentDashboard -> StudentDashboard(
                username = screen.username,
                onLogout = { currentScreen = Screen.Login }
            )
            is Screen.TeacherDashboard -> TeacherDashboard(
                username = screen.username,
                onLogout = { currentScreen = Screen.Login }
            )
        }
    }
}
