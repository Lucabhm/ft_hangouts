package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.ui.components.HeaderColorCard
import com.example.ft_hangouts.ui.components.LanguageCard

@Preview
@Composable
fun SettingsScreen(modifier: Modifier = Modifier, navController: NavController) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
            .verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        HeaderColorCard(modifier, navController)
        LanguageCard(modifier)
    }
}