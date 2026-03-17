package com.example.ft_hangouts.ui.addContact

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.repository.UIResult
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
    private val _state = MutableSharedFlow<UIResult<Long>>()
    val state = _state.asSharedFlow()

    fun saveContact() {
        viewModelScope.launch {
            if (phoneNumber.isNullOrBlank())
                _state.emit(UIResult.NotFound("Need to enter a Phone number"))
            else {
                val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                val current = sdf.format(Date())

                _state.emit(
                    contactRepository.createContact(
                        Contact(
                            null,
                            firstName,
                            lastName,
                            phoneNumber!!,
                            profilePic,
                            null,
                            current
                        )
                    )
                )
            }
        }
    }
}