package com.example.ft_hangouts.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.ft_hangouts.data.model.BottomNavItems
import com.example.ft_hangouts.data.model.NavResult

@Composable
fun BottomBar(currentRoute: NavResult, onClick: (NavResult) -> Unit) {
    val items = listOf(
        BottomNavItems.Chats,
        BottomNavItems.Settings
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { onClick(item.route) },
                icon = { Icon(item.icon, contentDescription = "bottom_icon") },
                label = { Text(stringResource(item.label)) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}
