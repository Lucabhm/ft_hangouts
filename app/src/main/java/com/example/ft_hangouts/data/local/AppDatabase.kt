package com.example.ft_hangouts.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.ContactContract.MessageEntry
import com.example.ft_hangouts.data.local.ContactContract.ThemeColorEntry

private const val SQL_CREATE_CONTACT = "CREATE TABLE IF NOT EXISTS ${ContactEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${ContactEntry.COLUMN_FIRST_NAME} TEXT," +
        "${ContactEntry.COLUMN_LAST_NAME} TEXT," +
        "${ContactEntry.COLUMN_PHONE_NUMBER} TEXT NOT NULL UNIQUE," +
        "${ContactEntry.COLUMN_PROFILE_PIC} TEXT," +
        "${ContactEntry.COLUMN_EMAIL} TEXT," +
        "${ContactEntry.COLUMN_LAST_MSG} INTEGER," +
        "${ContactEntry.COLUMN_CREATED_AT} INTEGER)"

private const val SQL_CREATE_MESSAGE = "CREATE TABLE IF NOT EXISTS ${MessageEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${MessageEntry.COLUMN_MESSAGE} TEXT," +
        "${MessageEntry.COLUMN_FROM_ID} INTEGER NOT NULL," +
        "${MessageEntry.COLUMN_SEND_TO_ID} INTEGER NOT NULL," +
        "${MessageEntry.COLUMN_CREATED_AT} INTEGER," +
        "FOREIGN KEY (${MessageEntry.COLUMN_FROM_ID})" +
        "REFERENCES ${ContactEntry.TABLE_NAME} (${BaseColumns._ID})" +
        "ON DELETE CASCADE, " +
        "FOREIGN KEY (${MessageEntry.COLUMN_SEND_TO_ID})" +
        "REFERENCES ${ContactEntry.TABLE_NAME} (${BaseColumns._ID})" +
        "ON DELETE CASCADE)"

private const val SQL_CREATE_THEME_COLOR =
    "CREATE TABLE IF NOT EXISTS ${ThemeColorEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${ThemeColorEntry.COLUMN_COLOR} INTEGER NOT NULL)"

private const val SQL_DROP_CONTACT = "DROP TABLE IF EXISTS ${ContactEntry.TABLE_NAME}"
private const val SQL_DROP_MESSAGE = "DROP TABLE IF EXISTS ${MessageEntry.TABLE_NAME}"
private const val SQL_DROP_THEME_COLOR = "DROP TABLE IF EXISTS ${ThemeColorEntry.TABLE_NAME}"

class AppDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)

        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CONTACT)
        db.execSQL(SQL_CREATE_MESSAGE)
        db.execSQL(SQL_CREATE_THEME_COLOR)

        createOwnContact(db)
        addThemeColor(db)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL(SQL_DROP_CONTACT)
        db.execSQL(SQL_DROP_MESSAGE)
        db.execSQL(SQL_DROP_THEME_COLOR)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Chat.db"
    }

    private fun createOwnContact(db: SQLiteDatabase) {
        val current = System.currentTimeMillis()
        val values = ContentValues().apply {
            put(ContactEntry.COLUMN_PHONE_NUMBER, "self")
            put(ContactEntry.COLUMN_CREATED_AT, current)
        }
        val check = db.insert(ContactEntry.TABLE_NAME, null, values)

        if (check == -1L) {
            Log.d("test", "self Creation Failed")
        }
    }

    private fun addThemeColor(db: SQLiteDatabase) {
        val color = Color.Black.toColorLong()
        val values = ContentValues().apply {
            put(ThemeColorEntry.COLUMN_COLOR, color)
        }

        val check = db.insert(ThemeColorEntry.TABLE_NAME, null, values)
        if (check == -1L) {
            Log.d("test", "Theme Color error")
        }
    }
}