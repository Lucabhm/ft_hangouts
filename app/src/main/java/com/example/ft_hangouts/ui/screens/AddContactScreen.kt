package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showSystemUi = true)
@Composable
fun AddContactScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        var text by remember { mutableStateOf("") }

        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text") },
        )
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Text") },
        )
    }
}

