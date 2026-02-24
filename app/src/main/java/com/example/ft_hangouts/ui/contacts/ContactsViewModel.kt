package com.example.ft_hangouts.ui.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    fun loadContacts(): StateFlow<UIResult<List<Contact>>> {
        return contactRepository.getAllContacts().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UIResult.Loading
        )
    }

    fun deleteContact(contactId: Long) {
        viewModelScope.launch {
            contactRepository.deleteContact(contactId)
        }
    }
}