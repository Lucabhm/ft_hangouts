package com.example.ft_hangouts.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.ui.components.HeaderColorCard
import com.example.ft_hangouts.ui.components.LanguageCard

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
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