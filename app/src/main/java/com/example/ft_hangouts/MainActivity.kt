package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.ft_hangouts.ui.addContact.AddContactViewModel
import com.example.ft_hangouts.ui.call.CallViewModel
import com.example.ft_hangouts.ui.chats.ChatsViewModel
import com.example.ft_hangouts.ui.message.MessageViewModel
import com.example.ft_hangouts.ui.navigation.AppNavigation
import com.example.ft_hangouts.ui.navigation.NavViewModel
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme
import kotlin.getValue

class MainActivity : ComponentActivity() {
    private val container by lazy { (application as FtHangouts).container }

    private val _navViewModel = NavViewModel()
    private val _chatViewModel by lazy { ChatsViewModel(container.contactRepo) }
    private val _callViewModel by lazy { CallViewModel(container.callRepo) }
    private val _messageViewModel by lazy { MessageViewModel(container.messageRepo, container.smsRepository) }
    private val _addContactViewModel by lazy { AddContactViewModel(container.contactRepo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.BROADCAST_SMS
            ),
            1

        )

        setContent {
            Ft_hangoutsTheme {
                AppNavigation(
                    modifier = Modifier,
                    _navViewModel,
                    _chatViewModel,
                    _messageViewModel,
                    _callViewModel,
                    _addContactViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        applicationContext.deleteDatabase("Chat.db")
    }
}
