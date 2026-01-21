package com.example.ft_hangouts.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("messages")
data class Message(
    @PrimaryKey() @ColumnInfo("id") val id: Number,
    @ColumnInfo("message") val message: String,
    @ColumnInfo("form_id") val fromId: Number,
    @ColumnInfo("created_at") val createdAt: String
)
