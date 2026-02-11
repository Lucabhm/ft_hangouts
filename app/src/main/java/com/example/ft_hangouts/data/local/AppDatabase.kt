package com.example.ft_hangouts.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.ContactContract.CallEntry
import com.example.ft_hangouts.data.local.ContactContract.MessageEntry

private const val SQL_CREATE_CONTACT = "CREATE TABLE IF NOT EXISTS ${ContactEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${ContactEntry.COLUMN_FIRST_NAME} TEXT," +
        "${ContactEntry.COLUMN_LAST_NAME} TEXT," +
        "${ContactEntry.COLUMN_PHONE_NUMBER} TEXT NOT NULL UNIQUE," +
        "${ContactEntry.COLUMN_PROFILE_PIC} INTEGER," +
        "${ContactEntry.COLUMN_LAST_MSG} TEST," +
        "${ContactEntry.COLUMN_CREATED_AT} TEXT)"

private const val SQL_CREATE_CALL = "CREATE TABLE IF NOT EXISTS ${CallEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${CallEntry.COLUMN_CONTACT_ID} INTEGER NOT NULL," +
        "${CallEntry.COLUMN_CREATED_AT} TEXT," +
        "FOREIGN KEY (${CallEntry.COLUMN_CONTACT_ID})" +
        "REFERENCES ${ContactEntry.TABLE_NAME} (${BaseColumns._ID})" +
        "ON DELETE CASCADE)"

private const val SQL_CREATE_MESSAGE = "CREATE TABLE IF NOT EXISTS ${MessageEntry.TABLE_NAME} (" +
        "${BaseColumns._ID} INTEGER PRIMARY KEY," +
        "${MessageEntry.COLUMN_MESSAGE} TEXT," +
        "${MessageEntry.COLUMN_FROM_ID} INTEGER NOT NULL," +
        "${MessageEntry.COLUMN_SEND_TO_ID} INTEGER NOT NULL," +
        "${MessageEntry.COLUMN_CREATED_AT} TEXT," +
        "FOREIGN KEY (${MessageEntry.COLUMN_FROM_ID})" +
        "REFERENCES ${ContactEntry.TABLE_NAME} (${BaseColumns._ID})" +
        "ON DELETE CASCADE, " +
        "FOREIGN KEY (${MessageEntry.COLUMN_SEND_TO_ID})" +
        "REFERENCES ${ContactEntry.TABLE_NAME} (${BaseColumns._ID})" +
        "ON DELETE CASCADE)"

private const val SQL_DROP_CONTACT = "DROP TABLE IF EXISTS ${ContactEntry.TABLE_NAME}"
private const val SQL_DROP_CALL = "DROP TABLE IF EXISTS ${CallEntry.TABLE_NAME}"
private const val SQL_DROP_MESSAGE = "DROP TABLE IF EXISTS ${MessageEntry.TABLE_NAME}"

class AppDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_CONTACT)
        db.execSQL(SQL_CREATE_CALL)
        db.execSQL(SQL_CREATE_MESSAGE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL(SQL_DROP_CONTACT)
        db.execSQL(SQL_DROP_CALL)
        db.execSQL(SQL_DROP_MESSAGE)
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Chat.db"
    }
}