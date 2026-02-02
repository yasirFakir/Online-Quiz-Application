package com.quiz.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var studentLevel by remember { mutableStateOf("University") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("STUDENT") }

    val levels = listOf("School", "College", "University", "Masters", "PhD")
    var showLevelMenu by remember { mutableStateOf(false) }

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
                onClick = onNavigateToLogin,
                colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                shape = RectangleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier.height(44.dp)
            ) {
                Text("BACK TO LOGIN", color = Color.White, fontWeight = FontWeight.Bold)
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
                         contentDescription = "Signup Illustration",
                         modifier = Modifier.fillMaxWidth(0.7f),
                         contentScale = ContentScale.FillWidth
                     )
                }

                // Right Signup Form - Takes 50% width
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .width(450.dp)
                            .border(1.dp, GoniaEduColors.Border, RectangleShape)
                            .padding(vertical = 40.dp, horizontal = 40.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "CREATE ACCOUNT",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Black,
                            color = GoniaEduColors.Primary
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedRole == "STUDENT",
                                onClick = { selectedRole = "STUDENT" },
                                colors = RadioButtonDefaults.colors(selectedColor = GoniaEduColors.Primary)
                            )
                            Text("Student", modifier = Modifier.padding(end = 20.dp), fontWeight = FontWeight.Medium)
                            
                            RadioButton(
                                selected = selectedRole == "TEACHER",
                                onClick = { selectedRole = "TEACHER" },
                                 colors = RadioButtonDefaults.colors(selectedColor = GoniaEduColors.Primary)
                            )
                            Text("Teacher", fontWeight = FontWeight.Medium)
                        }

                        // Updated Order: Credentials first
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Username", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = username,
                                onValueChange = { username = it },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Password", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = password,
                                onValueChange = { password = it },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        Divider(modifier = Modifier.padding(vertical = 4.dp), color = GoniaEduColors.Border)

                        // Personal Info
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Full Name", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = fullName,
                                onValueChange = { fullName = it },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Email", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Phone Number", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = phoneNumber,
                                onValueChange = { phoneNumber = it },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = GoniaEduColors.Primary,
                                    unfocusedBorderColor = GoniaEduColors.Border
                                )
                            )
                        }

                        if (selectedRole == "STUDENT") {
                            Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text("Student Level", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedButton(
                                        onClick = { showLevelMenu = true },
                                        modifier = Modifier.fillMaxWidth().height(50.dp),
                                        shape = RectangleShape,
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = GoniaEduColors.TextMain)
                                    ) {
                                        Text(studentLevel)
                                    }
                                    DropdownMenu(
                                        expanded = showLevelMenu,
                                        onDismissRequest = { showLevelMenu = false },
                                        modifier = Modifier.width(370.dp)
                                    ) {
                                        levels.forEach { level ->
                                            DropdownMenuItem(onClick = {
                                                studentLevel = level
                                                showLevelMenu = false
                                            }) {
                                                Text(level)
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = onSignupSuccess,
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("SIGN UP", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}