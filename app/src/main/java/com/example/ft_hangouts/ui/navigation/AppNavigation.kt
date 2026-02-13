package com.example.ft_hangouts.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ft_hangouts.ui.components.AddContactButton
import com.example.ft_hangouts.ui.components.BottomBar
import com.example.ft_hangouts.ui.components.TopBar
import com.example.ft_hangouts.ui.addContact.AddContactScreen
import com.example.ft_hangouts.ui.call.CallScreen
import com.example.ft_hangouts.ui.chats.ChatsScreen
import com.example.ft_hangouts.ui.message.MessagesScreen
import com.example.ft_hangouts.ui.screens.SettingsScreen

@Composable
fun AppNavigation(viewModel: ContactViewModel = viewModel()) {
    val navController = rememberNavController()
    val getRoute = navController.currentBackStackEntryAsState()
    val currentRoute = getRoute.value?.destination?.route
    val hideBar = listOf("CreateContact", "Messages")
    val checkHideBar = hideBar.any { currentRoute?.startsWith(it) == true }
    val checkContact by viewModel.selectedContact.collectAsState()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            if (!checkHideBar) {
                BottomBar(navController)
            }
        },
        topBar = { if (checkContact == null) TopBar(navController) },
        floatingActionButton = {
            if (currentRoute?.startsWith("Chats") == true) AddContactButton(
                navController
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Chats",
            modifier = Modifier.padding((innerPadding))
        ) {
            composable("Chats") { ChatsScreen(navController) }
            composable("Calls") { CallScreen() }
            composable("Settings") { SettingsScreen(navController = navController) }
            composable("CreateContact") { AddContactScreen() }
            composable(
                "Messages/{image}/{userName}", arguments = listOf(
                    navArgument("image") { type = NavType.IntType },
                    navArgument("userName") { type = NavType.StringType })
            ) {
                MessagesScreen()
            }
        }
    }
}