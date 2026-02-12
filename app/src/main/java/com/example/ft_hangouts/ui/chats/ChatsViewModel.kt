package com.example.ft_hangouts.ui.chats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatsViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    private val _data = MutableStateFlow<UIResult<List<Contact>>>(UIResult.Loading)
    val data: StateFlow<UIResult<List<Contact>>> = _data

    fun loadContacts() {
        viewModelScope.launch {
            _data.value = contactRepository.getAllContacts()
        }
    }
}