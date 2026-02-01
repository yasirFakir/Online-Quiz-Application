package com.quiz.ui.screens.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.quiz.ui.theme.GoniaEduColors

@Composable
fun StudentDashboard(
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
                        Text("QUIZE", fontWeight = FontWeight.Black, color = GoniaEduColors.Primary)
                        Spacer(modifier = Modifier.width(40.dp))
                        TextButton(onClick = {}) { Text("Home", color = GoniaEduColors.Primary) }
                        TextButton(onClick = {}) { Text("Enroll", color = GoniaEduColors.TextMain) }
                        TextButton(onClick = {}) { Text("Leaderboard", color = GoniaEduColors.TextMain) }
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
            Text(
                text = "My Courses",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 250.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(6) { index ->
                    CourseCard("Course ${index + 1}")
                }
            }
        }
    }
}

@Composable
fun CourseCard(name: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = 0.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, GoniaEduColors.Border),
        shape = androidx.compose.ui.graphics.RectangleShape
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
