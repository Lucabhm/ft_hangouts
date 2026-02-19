package com.example.ft_hangouts.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ft_hangouts.data.model.NavResult


sealed class BottomNavItems(val route: NavResult, val icon: ImageVector, val label: String) {
    object Chats : BottomNavItems(NavResult.ContactsScreen, Icons.Default.AccountBox, "Chats")
    object Calls : BottomNavItems(NavResult.CallScreen, Icons.Default.Call, "Calls")
    object Settings : BottomNavItems(NavResult.SettingsScreen, Icons.Default.Settings, "Settings")
}
