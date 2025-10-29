package com.example.ft_hangouts.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun AddContactButton(navController: NavController,) {
    SmallFloatingActionButton(
        onClick = { navController.navigate("createContact") },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Add Contact")
    }
}
