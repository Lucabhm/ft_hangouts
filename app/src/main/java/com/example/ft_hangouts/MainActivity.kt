package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.example.ft_hangouts.data.local.AppDatabaseHelper
import com.example.ft_hangouts.data.local.dao.ContactDao
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.ui.chats.ChatsScreen
import com.example.ft_hangouts.ui.chats.ChatsViewModel
import com.example.ft_hangouts.ui.navigation.AppNavigation
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = AppDatabaseHelper(this)
        val contactDao = ContactDao(db)
        val contactRepository = ContactRepository(contactDao)
        val contactViewModel = ChatsViewModel(contactRepository)

        setContent {
            Ft_hangoutsTheme {
                ChatsScreen(viewModel = contactViewModel)
            }
        }
    }
}
