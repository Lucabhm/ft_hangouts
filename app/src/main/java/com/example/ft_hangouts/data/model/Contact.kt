package com.example.ft_hangouts.data.model

data class Contact(
    val id: Long?,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String,
    val profilePicture: Int?,
    val lastMsg: String?,
    val createdAt: String
)
