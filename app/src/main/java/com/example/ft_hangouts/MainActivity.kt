package com.example.ft_hangouts

import android.content.BroadcastReceiver
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.ft_hangouts.data.local.AppContainer
import com.example.ft_hangouts.ui.addContact.AddContactViewModel
import com.example.ft_hangouts.ui.call.CallViewModel
import com.example.ft_hangouts.ui.chats.ChatsViewModel
import com.example.ft_hangouts.ui.message.MessageViewModel
import com.example.ft_hangouts.ui.message.SmsReceiver
import com.example.ft_hangouts.ui.navigation.AppNavigation
import com.example.ft_hangouts.ui.navigation.NavViewModel
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme

class MainActivity : ComponentActivity() {
    private val _container = AppContainer(this)
    private val navViewModel = NavViewModel()
    private val chatViewModel = ChatsViewModel(_container.contactRepo)
    private val callViewModel = CallViewModel(_container.callRepo)
    private val messageViewModel = MessageViewModel(_container.messageRepo, _container.smsRepository)
    private val addContactViewModel = AddContactViewModel(_container.contactRepo)

    private val smsReceiver = SmsReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Ft_hangoutsTheme {
                AppNavigation(
                    modifier = Modifier,
                    navViewModel,
                    chatViewModel,
                    messageViewModel,
                    callViewModel,
                    addContactViewModel
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        applicationContext.deleteDatabase("Chat.db")
    }
}
