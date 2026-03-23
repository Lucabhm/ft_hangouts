package com.example.ft_hangouts.ui.updateContact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.ContactFormError
import com.example.ft_hangouts.data.model.ContactUIState
import com.example.ft_hangouts.data.model.UIResult
import com.example.ft_hangouts.data.repository.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    var firstName by mutableStateOf<String?>(null)
    var lastName by mutableStateOf<String?>(null)
    var phoneNumber by mutableStateOf<String?>(null)
    var profilePic by mutableStateOf<String?>(null)
    private val _state = MutableStateFlow<ContactUIState>(ContactUIState.Loading)
    val state: StateFlow<ContactUIState> = _state

    fun updateContact(contact: Contact) {
        val test = ContactFormError()

        if (!phoneNumberCheck())
            test.phoneNumber = "Invalid phone number"
        if (!lastNameCheck())
            test.lastName = "Invalid last name"
        if (!firstNameCheck())
            test.firstName = "Invalid first name"

        if (test.phoneNumber != null || test.lastName != null || test.firstName != null)
            _state.value = ContactUIState.InputError(msg = test)
        else {
            viewModelScope.launch {
                val result = contactRepository.updateContact(
                    contact.id!!,
                    firstName,
                    lastName,
                    phoneNumber,
                    profilePic,
                    null,
                )

                when (result) {
                    is UIResult.Loading -> {
                        _state.value = ContactUIState.Loading
                    }
                    is UIResult.NotFound -> {
                        _state.value = ContactUIState.DataBaseError(result.msg)
                    }
                    is UIResult.Success -> {
                        _state.value = ContactUIState.Success
                    }
                    else -> {
                        _state.value = ContactUIState.DataBaseError("Undefined Database Error")
                    }
                }
            }
        }
    }

    fun resetState() {
        _state.value = ContactUIState.Loading
    }

    private fun phoneNumberCheck(): Boolean {
        val phoneNumberRegex = Regex("^\\+?[0-9]{7,15}$")

        return phoneNumber?.let { phoneNumberRegex.matches(it) } == true
    }

    private fun lastNameCheck(): Boolean {
        return lastName?.let { it.length in 0..10 } ?: true
    }

    private fun firstNameCheck(): Boolean {
        return firstName?.let { it.length in 0..10 } ?: true
    }
}
