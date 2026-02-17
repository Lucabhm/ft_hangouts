package com.example.ft_hangouts.data.local.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.ContactContract.CallEntry
import com.example.ft_hangouts.data.local.toCall
import com.example.ft_hangouts.data.model.Call
import com.example.ft_hangouts.ui.components.ContactCard

class CallDao(private val dbHelper: SQLiteOpenHelper) {
    fun selectAll(): Result<List<Call>> {
        return try {
            val db = dbHelper.readableDatabase
            val calls = mutableListOf<Call>()

            val cursor = db.rawQuery(
                """
                SELECT c.${BaseColumns._ID} AS call_id, c.${CallEntry.COLUMN_CREATED_AT} AS call_created_at,
                ct.${BaseColumns._ID} AS contact_id, ct.${ContactEntry.COLUMN_FIRST_NAME}, ct.${ContactEntry.COLUMN_LAST_NAME}, ct.${ContactEntry.COLUMN_PHONE_NUMBER},
                ct.${ContactEntry.COLUMN_PROFILE_PIC}, ct.${ContactEntry.COLUMN_LAST_MSG}, ct.${ContactEntry.COLUMN_CREATED_AT} AS contact_created_at
                FROM ${CallEntry.TABLE_NAME} AS c
                INNER JOIN ${ContactEntry.TABLE_NAME} AS ct ON c.${CallEntry.COLUMN_CONTACT_ID} = ct.${BaseColumns._ID}
                ORDER BY c.${CallEntry.COLUMN_CREATED_AT} DESC
            """.trimIndent(), null
            )


            while (cursor.moveToNext()) {
                calls.add(cursor.toCall())
            }

            cursor.close()
            Result.success(calls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

//    fun insert(call: Call): Result<Long> {
//        return try {
//            val db = dbHelper.writableDatabase
//            val values = ContentValues().apply {
//                put(CallEntry.COLUMN_CONTACT_ID, call.contactId)
//                put(CallEntry.COLUMN_CREATED_AT, call.createdAt)
//            }
//
//            val id = db.insert(CallEntry.TABLE_NAME, null, values)
//            if (id == -1L) Result.failure(Exception("Call insert failed"))
//            else Result.success(id)
//        } catch (e: Exception) {
//            Result.failure(e)
//        }
//    }

    fun deleteById(callId: Long): Result<Int> {
        return try {
            val db = dbHelper.writableDatabase

            val count = db.delete(
                CallEntry.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(callId.toString())
            )

            if (count == 0) Result.failure(NoSuchElementException("Call not found"))
            else Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
