package com.example.ft_hangouts.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("messages")
data class Message(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Long = 0,
    @ColumnInfo("message") val message: String,
    @ColumnInfo("form_id") val fromId: Long,
    @ColumnInfo("created_at") val createdAt: String
)
