package com.quiz.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object GoniaEduColors {
    val Primary = Color(0xFF74B086)      // Sage Green (from image)
    val Secondary = Color(0xFF9BD4AD)    // Mint Green (from image)
    val Accent = Color(0xFF263135)       // Dark Charcoal
    val Background = Color(0xFFF3FCF6)   // Mint White (from image background)
    val Surface = Color(0xFFFFFFFF)
    val TextMain = Color(0xFF263135)     // Dark Charcoal (High contrast)
    val Border = Color(0xFFDAE6DF)       // Light Gray-Green
}

@Composable
fun GoniaEduTheme(content: @Composable () -> Unit) {
    val colors = lightColors(
        primary = GoniaEduColors.Primary,
        secondary = GoniaEduColors.Secondary,
        background = GoniaEduColors.Background,
        surface = GoniaEduColors.Surface,
        onPrimary = Color.White,
        onSecondary = GoniaEduColors.TextMain,
        onBackground = GoniaEduColors.TextMain,
        onSurface = GoniaEduColors.TextMain
    )

    MaterialTheme(
        colors = colors,
        shapes = MaterialTheme.shapes.copy(
            small = RoundedCornerShape(0.dp),  // Strictly 0 radius
            medium = RoundedCornerShape(0.dp),
            large = RoundedCornerShape(0.dp)
        ),
        content = content
    )
}