package com.example.ft_hangouts.data.local

import android.content.Context
import com.example.ft_hangouts.data.local.dao.ContactDao
import com.example.ft_hangouts.data.local.dao.MessageDao
import com.example.ft_hangouts.data.local.dao.ThemeColorDao
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.data.repository.SMSRepository
import com.example.ft_hangouts.data.repository.ThemeRepository

class AppContainer(context: Context) {
    private val _db = AppDatabaseHelper(context)
    private val _contactDao = ContactDao(_db)
    private val _messageDao = MessageDao(_db)
    private val _themeColorDao = ThemeColorDao(_db)
    val contactRepo = ContactRepository(_contactDao)
    val messageRepo = MessageRepository(_messageDao)
    val themeRepo = ThemeRepository(_themeColorDao)
    val smsRepository = SMSRepository(context)
}