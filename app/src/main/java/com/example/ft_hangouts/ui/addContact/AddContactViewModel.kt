package com.example.ft_hangouts.ui.addContact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.ContactUIState
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.ContactFormError
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.model.UIResult
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    var firstName by mutableStateOf<String?>(null)
    var lastName by mutableStateOf<String?>(null)
    var phoneNumber by mutableStateOf<String?>(null)
    var profilePic by mutableStateOf<String?>(null)
    private val _state = MutableSharedFlow<ContactUIState>()
    val state = _state.asSharedFlow()

    fun saveContact() {
        viewModelScope.launch {
            val test = ContactFormError()

            if (!phoneNumberCheck())
                test.phoneNumber = "Invalid phone number"
            if (!lastNameCheck())
                test.lastName = "Invalid last name"
            if (!firstNameCheck())
                test.firstName = "Invalid first name"

            if (test.phoneNumber != null || test.lastName != null || test.firstName != null)
                _state.emit(ContactUIState.InputError(msg = test))
            else {
                phoneNumber?.let {
                    val result = contactRepository.createContact(
                        firstName,
                        lastName,
                        it,
                        profilePic,
                    )


                    when (result) {
                        is UIResult.Loading -> {
                            _state.emit(ContactUIState.Loading)
                        }

                        is UIResult.NotFound -> {
                            _state.emit(ContactUIState.DataBaseError(result.msg))
                        }

                        is UIResult.Success -> {
                            _state.emit(ContactUIState.Success)
                        }

                        else -> {
                            _state.emit(ContactUIState.DataBaseError("Undefined Database Error"))
                        }
                    }
                }
            }
        }
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