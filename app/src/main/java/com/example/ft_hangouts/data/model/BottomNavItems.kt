package com.example.ft_hangouts.data.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.NavResult


sealed class BottomNavItems(val route: NavResult, val icon: ImageVector, val label: Int) {
    object Chats : BottomNavItems(NavResult.ContactsScreen, Icons.Default.AccountBox,
        R.string.bottom_bar_chats
    )
    object Calls : BottomNavItems(NavResult.CallScreen, Icons.Default.Call, 0)
    object Settings : BottomNavItems(NavResult.SettingsScreen, Icons.Default.Settings, R.string.bottom_bar_settings)
}
