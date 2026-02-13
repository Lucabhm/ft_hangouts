package com.example.ft_hangouts.data.local.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.local.ContactContract.MessageEntry
import com.example.ft_hangouts.data.local.toMessage
import com.example.ft_hangouts.data.model.Message

class MessageDao(private val dbHelper: SQLiteOpenHelper) {
    fun selectAll(): Result<List<Message>> {
        return try {
            val db = dbHelper.readableDatabase
            val messages = mutableListOf<Message>()

            db.query(
                MessageEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "${MessageEntry.COLUMN_CREATED_AT} DESC"
            ).use { cursor ->
                while (cursor.moveToNext()) {
                    messages.add(cursor.toMessage())
                }
            }
            Result.success(messages)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun insert(msg: Message): Result<Long> {
        return try {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(MessageEntry.COLUMN_MESSAGE, msg.message)
                put(MessageEntry.COLUMN_FROM_ID, msg.fromId)
                put(MessageEntry.COLUMN_SEND_TO_ID, msg.sendToId)
                put(MessageEntry.COLUMN_CREATED_AT, msg.createdAt)
            }

            val id = db.insert(MessageEntry.TABLE_NAME, null, values)
            if (id == -1L)
                Result.failure(Exception("Message insert failed"))
            else
                Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun deleteById(messageId: Long): Result<Int> {
        return try {
            val db = dbHelper.writableDatabase

            val count = db.delete(
                MessageEntry.TABLE_NAME,
                "${BaseColumns._ID} = ?",
                arrayOf(messageId.toString())
            )

            if (count == 0)
                Result.failure(NoSuchElementException("Message not found"))
            else
                Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}