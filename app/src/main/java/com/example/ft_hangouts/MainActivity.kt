package com.example.ft_hangouts

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import com.example.ft_hangouts.ui.addContact.AddContactViewModel
import com.example.ft_hangouts.ui.contacts.ContactsViewModel
import com.example.ft_hangouts.ui.message.MessageViewModel
import com.example.ft_hangouts.ui.navigation.AppNavigation
import com.example.ft_hangouts.ui.navigation.NavViewModel
import com.example.ft_hangouts.ui.settings.SettingsViewModel
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme
import com.example.ft_hangouts.ui.updateContact.UpdateContactViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    private val container by lazy { (application as FtHangouts).container }

    private val _navViewModel = NavViewModel()
    private val _chatViewModel by lazy { ContactsViewModel(container.contactRepo) }
    private val _messageViewModel by lazy {
        MessageViewModel(
            container.messageRepo,
            container.contactRepo,
            container.smsRepository
        )
    }
    private val _addContactViewModel by lazy { AddContactViewModel(container.contactRepo) }

    private val _updateContactViewModel by lazy { UpdateContactViewModel(container.contactRepo) }

    private val _settingsViewModel by lazy { SettingsViewModel(container.themeRepo) }
    private var _timeStopped = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.SEND_SMS,
                android.Manifest.permission.RECEIVE_SMS,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.BROADCAST_SMS,
                android.Manifest.permission.CALL_PHONE
            ),
            1

        )

        _settingsViewModel.loadThemeColor()
        setContent {
            val themeColor = _settingsViewModel.color

            Ft_hangoutsTheme(themeColor) {
                AppNavigation(
                    modifier = Modifier,
                    _navViewModel,
                    _chatViewModel,
                    _messageViewModel,
                    _addContactViewModel,
                    _updateContactViewModel,
                    _settingsViewModel
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (_timeStopped != 0L) {
            val date = Date(_timeStopped)
            val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val onlyTime = outputFormat.format(date)

            Toast.makeText(this, "App stopped at $onlyTime", Toast.LENGTH_LONG).show()
            _timeStopped = 0
        }
    }

    override fun onStop() {
        super.onStop()
        _timeStopped = System.currentTimeMillis()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
