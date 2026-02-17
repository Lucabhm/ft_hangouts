package com.example.ft_hangouts.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.ft_hangouts.ui.addContact.AddContactScreen
import com.example.ft_hangouts.ui.addContact.AddContactViewModel
import com.example.ft_hangouts.ui.call.CallScreen
import com.example.ft_hangouts.ui.call.CallViewModel
import com.example.ft_hangouts.ui.chats.ChatsScreen
import com.example.ft_hangouts.ui.chats.ChatsViewModel
import com.example.ft_hangouts.ui.components.AddContactButton
import com.example.ft_hangouts.ui.components.BottomBar
import com.example.ft_hangouts.ui.components.TopBar
import com.example.ft_hangouts.ui.message.MessageViewModel
import com.example.ft_hangouts.ui.message.MessagesScreen
import com.example.ft_hangouts.ui.settings.SettingsScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navViewModel: NavViewModel,
    chatsViewModel: ChatsViewModel,
    messageViewModel: MessageViewModel,
    callViewModel: CallViewModel,
    addContactViewModel: AddContactViewModel
) {
    val state by navViewModel.currentScreen.collectAsState()

    val showTopBar = state !is NavResult.AddContactScreen && state !is NavResult.ChatScreen
    val showFloatingButton = state is NavResult.ContactsScreen

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        bottomBar = {
            if (showTopBar) {
                BottomBar(state, onClick = {screen -> navViewModel.changeStack(screen)})
            }
        },
        topBar = { TopBar(currentRoute = state, goBack = { navViewModel.goBack() }) },
        floatingActionButton = {
            if (showFloatingButton) AddContactButton(onClick = { navViewModel.navigateTo(NavResult.AddContactScreen) })
        }
    ) { innerPadding ->
        when (state) {
            is NavResult.ContactsScreen -> {
                ChatsScreen(Modifier.padding((innerPadding)), chatsViewModel, onClick = { contact ->
                    navViewModel.navigateTo(
                        NavResult.ChatScreen(contact)
                    )
                })
            }

            is NavResult.ChatScreen -> {
                val contact = (state as NavResult.ChatScreen).contact
                MessagesScreen(Modifier.padding((innerPadding)), messageViewModel)
            }

            is NavResult.CallScreen -> {
                CallScreen(Modifier.padding((innerPadding)), callViewModel)
            }

            is NavResult.AddContactScreen -> {
                AddContactScreen(Modifier.padding((innerPadding)), addContactViewModel)
            }

            is NavResult.SettingsScreen -> {
                SettingsScreen(Modifier.padding((innerPadding)))
            }
        }
    }
}
