package com.quiz.ui.screens.auth

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quiz.ui.theme.GoniaEduColors

enum class ForgotPasswordStep {
    REQUEST_CODE,
    VERIFY_CODE,
    RESET_PASSWORD,
    SUCCESS
}

@Composable
fun ForgotPasswordScreen(
    onNavigateBack: () -> Unit
) {
    var step by remember { mutableStateOf(ForgotPasswordStep.REQUEST_CODE) }
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                onClick = onNavigateBack,
                colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                shape = RectangleShape,
                elevation = ButtonDefaults.elevation(0.dp, 0.dp),
                modifier = Modifier.height(44.dp)
            ) {
                Text("BACK", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }

        Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .width(450.dp)
                    .border(1.dp, GoniaEduColors.Border, RectangleShape)
                    .padding(vertical = 40.dp, horizontal = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (step) {
                    ForgotPasswordStep.REQUEST_CODE -> {
                        Text(text = "FORGOT PASSWORD", fontSize = 28.sp, fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
                        Text(text = "Enter your email address and we'll send you a 6-digit verification code.", fontSize = 14.sp, color = GoniaEduColors.TextMain)
                        
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Email Address", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = email,
                                onValueChange = { email = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = GoniaEduColors.Primary, unfocusedBorderColor = GoniaEduColors.Border)
                            )
                        }

                        Button(
                            onClick = { step = ForgotPasswordStep.VERIFY_CODE },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("SEND CODE", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    ForgotPasswordStep.VERIFY_CODE -> {
                        Text(text = "VERIFY CODE", fontSize = 28.sp, fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
                        Text(text = "Enter the 6-digit code sent to $email", fontSize = 14.sp, color = GoniaEduColors.TextMain)
                        
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Verification Code", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = code,
                                onValueChange = { if (it.length <= 6) code = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = GoniaEduColors.Primary, unfocusedBorderColor = GoniaEduColors.Border)
                            )
                        }

                        Button(
                            onClick = { step = ForgotPasswordStep.RESET_PASSWORD },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("VERIFY", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    ForgotPasswordStep.RESET_PASSWORD -> {
                        Text(text = "NEW PASSWORD", fontSize = 28.sp, fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
                        
                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("New Password", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = newPassword,
                                onValueChange = { newPassword = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = GoniaEduColors.Primary, unfocusedBorderColor = GoniaEduColors.Border)
                            )
                        }

                        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text("Confirm Password", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                            OutlinedTextField(
                                value = confirmPassword,
                                onValueChange = { confirmPassword = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RectangleShape,
                                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = GoniaEduColors.Primary, unfocusedBorderColor = GoniaEduColors.Border)
                            )
                        }

                        Button(
                            onClick = { step = ForgotPasswordStep.SUCCESS },
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("RESET PASSWORD", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    ForgotPasswordStep.SUCCESS -> {
                        Icon(modifier = Modifier.size(64.dp), imageVector = Icons.Default.Lock, contentDescription = "Success", tint = GoniaEduColors.Primary)
                        Text(text = "SUCCESS!", fontSize = 28.sp, fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
                        Text(text = "Your password has been reset successfully.", fontSize = 14.sp, color = GoniaEduColors.TextMain)
                        
                        Button(
                            onClick = onNavigateBack,
                            modifier = Modifier.fillMaxWidth().height(44.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = GoniaEduColors.Primary),
                            shape = RectangleShape,
                            elevation = ButtonDefaults.elevation(0.dp, 0.dp)
                        ) {
                            Text("BACK TO LOGIN", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}