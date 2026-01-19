package com.example.ft_hangouts.ui.components

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun HeaderColorCard(modifier: Modifier = Modifier, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    var selectedColor by remember {mutableStateOf(Color.Red )}
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
    Card(
        modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = { expanded = !expanded })
    ) {
        Row(
            modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text("Change Header Color", fontSize = 25.sp)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier.fillMaxWidth()
        ) {
            colors.forEach { color ->
                DropdownMenuItem(
                    text = { MenuItemCard(color = color) },
                    onClick = { selectedColor = color },
                    trailingIcon = {if (selectedColor == color) { Icon(Icons.Default.Done, contentDescription = null) }}
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(modifier: Modifier = Modifier, color: Color) {
    Box(
        modifier
            .size(40.dp)
            .background(color)
    )
}
