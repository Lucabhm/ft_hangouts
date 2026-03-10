package com.example.ft_hangouts.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Ft_hangoutsTheme(
    primaryColor: Color,
    content: @Composable () -> Unit
) {
    val colorScheme = lightColorScheme(primary = primaryColor, onPrimary = Color.White)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}