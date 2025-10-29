package com.example.ft_hangouts.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.ft_hangouts.data.model.BottomNavItems

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        BottomNavItems.Chats,
        BottomNavItems.Calls,
        BottomNavItems.Settings
    )

    val getRoute = navController.currentBackStackEntryAsState()
    val currentRoute = getRoute.value?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                    }
                },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
