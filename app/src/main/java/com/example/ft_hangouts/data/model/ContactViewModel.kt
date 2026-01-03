package com.example.ft_hangouts.data.model

import androidx.lifecycle.ViewModel
import com.example.ft_hangouts.R
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactViewModel : ViewModel() {
    private val contacts = listOf(
        Contacts("1", "Anna", 123456789, R.drawable.cr),
        Contacts("2", "Tom", 987654321, R.drawable.cr),
        Contacts("3", "Chris", 5551234, R.drawable.cr),
        Contacts("4", "Hallo", 5551234, R.drawable.cr),
        Contacts("5", "Test", 5551234, R.drawable.cr),
        Contacts("6", "LOL", 5551234, R.drawable.cr),
        Contacts("7", "Mama", 5551234, R.drawable.cr),
        Contacts("8", "What the hell", 5551234, R.drawable.cr),
        Contacts("9", "MOIN MOIN", 5551234, R.drawable.cr),
    )

    private val _selectedContact = MutableStateFlow<Contacts?>(null)

    val selectedContact = _selectedContact.asStateFlow()

    fun selectContact(id: String) {
        _selectedContact.value = contacts.find { it.id == id }
    }

    fun clearSelection() {
        _selectedContact.value = null
    }
}