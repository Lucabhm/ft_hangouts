package com.example.ft_hangouts.ui.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ft_hangouts.data.model.ContactViewModel
import com.example.ft_hangouts.ui.components.BottomBar
import com.example.ft_hangouts.ui.components.TopBar
import com.example.ft_hangouts.ui.screens.AddContactScreen
import com.example.ft_hangouts.ui.screens.CallScreen
import com.example.ft_hangouts.ui.screens.ChatScreen
import com.example.ft_hangouts.ui.screens.ChatsScreen
import com.example.ft_hangouts.ui.screens.HeaderColorScreen
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
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (!checkHideBar) {
                BottomBar(navController)
            }
        },
        topBar = { if (checkContact == null) TopBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "chats",
            modifier = Modifier.padding((innerPadding))
        ) {
            composable("Chats") { ChatsScreen(navController) }
            composable("Calls") { CallScreen() }
            composable("Settings") { SettingsScreen(navController = navController) }
            composable("HeaderColor") { HeaderColorScreen() }
            composable("CreateContact") { AddContactScreen() }
            composable(
                "Messages/{image}/{userName}", arguments = listOf(
                    navArgument("image") { type = NavType.IntType },
                    navArgument("userName") { type = NavType.StringType })
            ) {
                ChatScreen()
            }
        }
    }
}