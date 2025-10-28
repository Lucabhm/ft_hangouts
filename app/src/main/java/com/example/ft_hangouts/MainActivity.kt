package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ft_hangouts.ui.screens.CallScreen
import com.example.ft_hangouts.ui.screens.ChatsScreen
import com.example.ft_hangouts.ui.screens.SettingsScreen
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ft_hangoutsTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController) },
                    topBar = { TopBar() }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "chats",
                        modifier = Modifier.padding((innerPadding))
                    ) {
                        composable("chats") { ChatsScreen() }
                        composable("calls") { CallScreen() }
                        composable("settings") { SettingsScreen() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { "ft_hangouts" },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf("chats", "calls", "settings")
    NavigationBar {
        items.forEach { route ->
            NavigationBarItem(
                selected = false,
                onClick = { navController.navigate(route) },
                icon = {
                    when (route) {
                        "chats" -> Icon(Icons.Default.AccountBox, contentDescription = route)
                        "calls" -> Icon(Icons.Default.Call, contentDescription = route)
                        "settings" -> Icon(Icons.Default.Settings, contentDescription = route)
                    }
                })
        }
    }
}
