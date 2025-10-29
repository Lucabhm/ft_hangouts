package com.example.ft_hangouts.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItems(val route: String, val icon: ImageVector, val label: String) {
    object Chats : BottomNavItems("Chats", Icons.Default.AccountBox, "Chats")
    object Calls : BottomNavItems("Calls", Icons.Default.Call, "Calls")
    object Settings : BottomNavItems("Settings", Icons.Default.Settings, "Settings")
}
