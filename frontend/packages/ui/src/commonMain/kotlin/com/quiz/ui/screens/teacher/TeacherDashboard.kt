package com.quiz.ui.screens.teacher

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quiz.ui.components.GoniaEduButton
import com.quiz.ui.theme.GoniaEduColors

@Composable
fun TeacherDashboard(
    username: String,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 0.dp,
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("QUIZE (Teacher)", fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
                        Spacer(modifier = Modifier.width(40.dp))
                        TextButton(onClick = {}) { Text("Home", color = GoniaEduColors.Primary) }
                        TextButton(onClick = {}) { Text("Add Course", color = GoniaEduColors.TextMain) }
                    }
                },
                actions = {
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(end = 16.dp)) {
                        Text(username, color = GoniaEduColors.TextMain)
                        Spacer(modifier = Modifier.width(10.dp))
                        TextButton(onClick = onLogout) { Text("Logout", color = Color.Red) }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dashboard Overview",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                GoniaEduButton(text = "New Course", onClick = {})
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { StatCard("Total Students", "124") }
                item { StatCard("Active Quizzes", "8") }
                item { StatCard("Average Score", "82%") }
            }
        }
    }
}

@Composable
fun StatCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(2.dp, GoniaEduColors.Primary),
        shape = androidx.compose.ui.graphics.RectangleShape
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(label, fontSize = 14.sp, color = Color.Gray)
            Text(value, fontSize = 32.sp, fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
        }
    }
}
