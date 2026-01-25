package com.example.ft_hangouts.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    fun selectAllMessages(): List<Message>

    @Query("SELECT * FROM messages WHERE id == :messageId")
    fun selectMessageById(messageId: Int): Message

    @Insert
    fun insertMessage(vararg message: Message)

    @Update
    fun updateMessage(vararg message: Message)

    @Delete
    fun deleteMessage(vararg message: Message)
}