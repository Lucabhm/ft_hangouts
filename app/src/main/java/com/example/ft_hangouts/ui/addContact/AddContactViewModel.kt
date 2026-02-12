package com.example.ft_hangouts.ui.addContact

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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddContactViewModel(private val contactRepository: ContactRepository) : ViewModel() {
    var firstName by mutableStateOf("")
    var lastName by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var profilePic by mutableIntStateOf(0)
    private val _state = MutableStateFlow<UIResult<Long>>(UIResult.Loading)
    val state: StateFlow<UIResult<Long>> = _state

    fun saveContact() {
        viewModelScope.launch {
            if (phoneNumber.isBlank())
                _state.value = UIResult.NotFound("Need to enter a Phone number")
            else {
                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                val current = sdf.format(Date())

                _state.value = contactRepository.createContact(
                    Contact(
                        null,
                        firstName,
                        lastName,
                        phoneNumber,
                        profilePic,
                        null,
                        current
                    )
                )
            }
        }
    }
}