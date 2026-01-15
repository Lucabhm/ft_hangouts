package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HeaderColorScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize().padding(5.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

    }
}

@Preview(showSystemUi = true)
@Composable
fun  TestScreen() {
    HeaderColorScreen()
}