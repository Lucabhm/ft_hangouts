package com.example.ft_hangouts.data.local.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteAbortException
import android.database.sqlite.SQLiteOpenHelper
import com.example.ft_hangouts.data.local.ContactContract.ThemeColorEntry
import com.example.ft_hangouts.data.local.toTheme
import com.example.ft_hangouts.data.model.ThemeColor

class ThemeColorDao(private val dbHelper: SQLiteOpenHelper) {
    fun select(): Result<ThemeColor> {
        return try {
            val db = dbHelper.readableDatabase

            db.query(ThemeColorEntry.TABLE_NAME, null, null, null, null, null, null)
                .use { cursor ->
                    if (cursor.moveToFirst()) {
                        Result.success(cursor.toTheme())
                    } else {
                        Result.failure(NoSuchElementException("No Theme found"))
                    }
                }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun insert(color: Long): Result<Long> {
        return try {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(ThemeColorEntry.COLUMN_COLOR, color)
            }

            val id = db.insert(ThemeColorEntry.TABLE_NAME, null, values)
            if (id == -1L) {
                Result.failure(SQLiteAbortException("Theme Color insert failed"))
            } else {
                Result.success(id)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun update(color: Long): Result<Int> {
        return try {
            val db = dbHelper.writableDatabase
            val values = ContentValues()

            color.let { values.put(ThemeColorEntry.COLUMN_COLOR, color) }

            val count = db.update(ThemeColorEntry.TABLE_NAME, values, null, null)
            if (count == 0) {
                Result.failure(SQLiteAbortException("Theme Color update error"))
            } else {
                Result.success(count)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
