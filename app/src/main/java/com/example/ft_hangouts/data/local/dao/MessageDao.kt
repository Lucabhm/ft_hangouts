package com.example.ft_hangouts.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ft_hangouts.data.local.entities.Message

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages")
    suspend fun selectAllMessages(): List<Message>

    @Query("SELECT * FROM messages WHERE id == :messageId")
    suspend fun selectMessageById(messageId: Int): Message

    @Insert
    suspend fun insertMessage(vararg message: Message)

    @Update
    suspend fun updateMessage(vararg message: Message)

    @Delete
    suspend fun deleteMessage(vararg message: Message)
}