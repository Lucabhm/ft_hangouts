package com.example.ft_hangouts.data.model

data class Message(
    val id: Long?,
    val message: String,
    val fromId: Long,
    val sendToId: Long,
    val createdAt: String
)
