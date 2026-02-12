package com.example.ft_hangouts.data.local.dao

import android.database.sqlite.SQLiteOpenHelper
import com.example.ft_hangouts.data.local.ContactContract.CallEntry
import com.example.ft_hangouts.data.local.toCall
import com.example.ft_hangouts.data.model.Call

class CallDao(private val dbHelper: SQLiteOpenHelper) {
    fun selectAll(): Result<List<Call>> {
        return try {
            val db = dbHelper.readableDatabase
            val calls = mutableListOf<Call>()

            db.query(
                CallEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                "${CallEntry.COLUMN_CREATED_AT} DESC"
            ).use { cursor ->
                while (cursor.moveToNext()) {
                    calls.add(cursor.toCall())
                }
            }
            Result.success(calls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
