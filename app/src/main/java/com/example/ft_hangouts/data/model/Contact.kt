package com.example.ft_hangouts.data.model

data class Contact(
    val id: Long?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String,
    val profilePicture: String?,
    val lastMsg: String?,
    val createdAt: String
)

class ContactFormError(
    var firstName: String? = null,
    var lastName: String? = null,
    var phoneNumber: String? = null,
)

sealed class ContactUIState {
    object Loading : ContactUIState()
    object Success : ContactUIState()
    data class InputError(val msg: ContactFormError = ContactFormError()) : ContactUIState()
    data class DataBaseError(val msg: String) : ContactUIState()
}

fun ContactUIState.phoneNumberError(): String? =
    (this as? ContactUIState.InputError)?.msg?.phoneNumber

fun ContactUIState.lastNameError(): String? =
    (this as? ContactUIState.InputError)?.msg?.lastName

fun ContactUIState.firstNameError(): String? =
    (this as? ContactUIState.InputError)?.msg?.firstName