package com.example.ft_hangouts.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    val getRoute = navController.currentBackStackEntryAsState()
    val currentRoute = getRoute.value?.destination?.route
    val showBack = listOf("CreateContact")

    CenterAlignedTopAppBar(
        title = { Text("ft_hangouts") },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            if (currentRoute in showBack) {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        }
    )
}
