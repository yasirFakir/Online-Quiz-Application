package com.quiz.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quiz.ui.theme.GoniaEduColors
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun LoginScreen(
    onLoginSuccess: (role: String) -> Unit,
    onNavigateToSignup: () -> Unit,
    onNavigateToForgotPassword: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        // ... (rest of the header code)
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Placeholder for Logo
                Surface(
                    modifier = Modifier.size(40.dp),
                    color = GoniaEduColors.Primary,
                    shape = RectangleShape
                ) {}
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "QUIZE",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                    color = GoniaEduColors.Primary,
                    letterSpacing = 1.sp
                )
            }
            Button(
                onClick = onNavigateToSignup,
                colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                shape = RectangleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier.height(44.dp)
            ) {
                Text("SIGN UP", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        // Center Content
        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left Image (Illustration) - Takes 50% width
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                     Image(
                         painter = painterResource("login_illustration.jpg"),
                         contentDescription = "Login Illustration",
                         modifier = Modifier.fillMaxWidth(0.7f),
                         contentScale = ContentScale.FillWidth
                     )
                }

                // Right Login Form - Takes 50% width
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .width(400.dp)
                            .border(1.dp, GoniaEduColors.Border, RectangleShape)
                            .padding(vertical = 48.dp, horizontal = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        Text(
                            text = "SIGN IN",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = GoniaEduColors.Primary
                        )

                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Username", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            Text("Password", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        TextButton(
                            onClick = onNavigateToForgotPassword,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            Text(
                                text = "Forgot Password?",
                                color = GoniaEduColors.Primary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { 
                                if (username.contains("teacher")) onLoginSuccess("TEACHER")
                                else onLoginSuccess("STUDENT")
                            },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("LOGIN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}