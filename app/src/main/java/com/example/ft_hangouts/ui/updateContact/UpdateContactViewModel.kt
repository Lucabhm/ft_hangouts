package com.example.ft_hangouts.ui.updateContact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.model.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    var firstName by mutableStateOf<String?>(null)
    var lastName by mutableStateOf<String?>(null)
    var phoneNumber by mutableStateOf<String?>(null)
    var profilePic by mutableStateOf<String?>(null)
    private val _state = MutableStateFlow<UIResult<Int>>(UIResult.Loading)
    val state: StateFlow<UIResult<Int>> = _state

    fun updateContact(contact: Contact) {
        viewModelScope.launch {
            _state.value = contactRepository.updateContact(
                contact.id!!,
                firstName,
                lastName,
                phoneNumber,
                profilePic,
                null,
            )
        }
    }

    fun resetState() {
        _state.value = UIResult.Loading
    }
}
