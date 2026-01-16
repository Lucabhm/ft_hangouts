package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HeaderColorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val colors = listOf(
            Color.Red,
            Color.Blue,
            Color.Cyan,
            Color.Gray,
            Color.LightGray,
            Color.Black,
            Color.DarkGray,
            Color.Green,
            Color.Magenta,
            Color.White,
            Color.Yellow
        )
        colors.forEach { color ->
            Box(
                modifier
                    .size(40.dp)
                    .background(color)
                    .border(shape = CutCornerShape(8.dp), color = Color.Black, width = 1.dp)
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun TestScreen() {
    HeaderColorScreen()
}