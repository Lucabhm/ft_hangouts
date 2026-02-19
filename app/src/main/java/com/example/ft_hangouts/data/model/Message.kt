package com.example.ft_hangouts.data.model

data class Message(
    val id: Long? = null,
    val message: String,
    val fromId: Long,
    val sendToId: Long,
    val createdAt: String
)
