package com.example.ft_hangouts.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ft_hangouts.ui.components.BottomBar
import com.example.ft_hangouts.ui.components.TopBar
import com.example.ft_hangouts.ui.screens.AddContactScreen
import com.example.ft_hangouts.ui.screens.CallScreen
import com.example.ft_hangouts.ui.screens.ChatScreen
import com.example.ft_hangouts.ui.screens.ChatsScreen
import com.example.ft_hangouts.ui.screens.SettingsScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val getRoute = navController.currentBackStackEntryAsState()
    val currentRoute = getRoute.value?.destination?.route
    val hideBar = listOf("CreateContact", "Messages")

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute !in hideBar) {
                BottomBar(navController)
            }
        },
        topBar = { TopBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "chats",
            modifier = Modifier.padding((innerPadding))
        ) {
            composable("Chats") { ChatsScreen(navController) }
            composable("Calls") { CallScreen() }
            composable("Settings") { SettingsScreen() }
            composable("CreateContact") { AddContactScreen() }
            composable("Messages") { ChatScreen() }
        }
    }
}