package com.example.ft_hangouts.data.model

sealed class NavResult(val route: String) {
    object ContactsScreen : NavResult("Contacts")
    object CallScreen : NavResult("Calls")
    object SettingsScreen : NavResult("Settings")
    object AddContactScreen : NavResult("AddContact")
    data class ChatScreen(val contact: Contact) : NavResult("Chat")
}