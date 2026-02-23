package com.example.ft_hangouts.data.local

import android.content.Context
import com.example.ft_hangouts.data.local.dao.CallDao
import com.example.ft_hangouts.data.local.dao.ContactDao
import com.example.ft_hangouts.data.local.dao.MessageDao
import com.example.ft_hangouts.data.repository.CallRepository
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.data.repository.SMSRepository
import com.example.ft_hangouts.ui.addContact.AddContactViewModel
import com.example.ft_hangouts.ui.call.CallViewModel
import com.example.ft_hangouts.ui.chats.ChatsViewModel
import com.example.ft_hangouts.ui.message.MessageViewModel
import com.example.ft_hangouts.ui.message.SmsReceiver
import com.example.ft_hangouts.ui.navigation.NavViewModel

class AppContainer(context: Context) {
    private val _db = AppDatabaseHelper(context)
    private val _contactDao = ContactDao(_db)
    private val _callDao = CallDao(_db)
    private val _messageDao = MessageDao(_db)
    val contactRepo = ContactRepository(_contactDao)
    val callRepo = CallRepository(_callDao)
    val messageRepo = MessageRepository(_messageDao)

    val smsRepository = SMSRepository(context)
}