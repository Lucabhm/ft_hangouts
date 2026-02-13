package com.example.ft_hangouts.data.local.dao

import android.database.sqlite.SQLiteOpenHelper
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.toContact
import com.example.ft_hangouts.data.local.toMessage
import com.example.ft_hangouts.data.model.Message

class MessageDao(private val dbHelper: SQLiteOpenHelper) {
    fun selectAll(): Result<List<Message>> {
        return try {
            val db = dbHelper.readableDatabase
            val messages = mutableListOf<Message>()

            db.query(
                ContactEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "${ContactEntry.COLUMN_LAST_MSG} DESC"
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
}