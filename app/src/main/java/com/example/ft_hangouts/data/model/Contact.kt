package com.example.ft_hangouts.data.model

data class Contact(
    val id: Long,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String,
    val profilePicture: String?,
    val email: String?,
    val lastMsg: Long?,
    val createdAt: Long
)

class ContactFormError(
    var firstName: Int? = null,
    var lastName: Int? = null,
    var phoneNumber: Int? = null,
    var email: Int? = null,
)

sealed class ContactUIState {
    object Loading : ContactUIState()
    object Success : ContactUIState()
    data class InputError(val msg: ContactFormError = ContactFormError()) : ContactUIState()
    data class DataBaseError(val msg: String) : ContactUIState()
}

fun ContactUIState.phoneNumberError(): Int? =
    (this as? ContactUIState.InputError)?.msg?.phoneNumber

fun ContactUIState.lastNameError(): Int? =
    (this as? ContactUIState.InputError)?.msg?.lastName

fun ContactUIState.firstNameError(): Int? =
    (this as? ContactUIState.InputError)?.msg?.firstName

fun ContactUIState.emailError(): Int? =
    (this as? ContactUIState.InputError)?.msg?.email