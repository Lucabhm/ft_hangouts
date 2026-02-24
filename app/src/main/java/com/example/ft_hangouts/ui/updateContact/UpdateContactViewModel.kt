package com.example.ft_hangouts.ui.updateContact

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var profilePic by mutableIntStateOf(0)
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

            Log.d("test", "state = ${_state.value}")
        }
    }
}
