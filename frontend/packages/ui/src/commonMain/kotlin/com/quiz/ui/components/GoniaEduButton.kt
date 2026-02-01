package com.quiz.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.quiz.ui.theme.GoniaEduColors

@Composable
fun GoniaEduButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(44.dp),
        shape = RectangleShape,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isPrimary) GoniaEduColors.Primary else Color.White,
            contentColor = if (isPrimary) Color.White else GoniaEduColors.Primary
        ),
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        border = if (!isPrimary) BorderStroke(1.dp, GoniaEduColors.Primary) else null
    ) {
        Text(text = text.uppercase())
    }
}
