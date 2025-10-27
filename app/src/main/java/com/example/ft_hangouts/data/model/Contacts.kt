package com.example.ft_hangouts.data.model

data class Contacts(
    val id: String,
    var name: String,
    var number: Int,
    var profilePicture: Int
)

data class Calls(
    val id: String,
    var contact: Contacts,
    var createdAt: String
)