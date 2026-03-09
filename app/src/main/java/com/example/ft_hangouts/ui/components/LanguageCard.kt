package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ft_hangouts.R

@Composable
fun LanguageCard(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedLang by remember { mutableIntStateOf(R.string.settings_language_input_en) }
    val languages = listOf(R.string.settings_language_input_ger, R.string.settings_language_input_en)
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable(onClick = {expanded = true})
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(stringResource(R.string.settings_language_button), fontSize = 25.sp)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = modifier.fillMaxWidth()
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text = { Text(stringResource(language)) },
                    onClick = { selectedLang = language },
                    trailingIcon = {if (selectedLang == language) { Icon(Icons.Default.Done, contentDescription = null) }}
                )
            }
        }
    }
}
