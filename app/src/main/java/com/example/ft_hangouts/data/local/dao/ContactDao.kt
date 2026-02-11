package com.example.ft_hangouts.data.local.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.toContact

class ContactDao(private val dbHelper: SQLiteOpenHelper) {
    fun selectAll(): List<Contact> {
        val db = dbHelper.readableDatabase
        val contacts = mutableListOf<Contact>()
        val cursor = db.query(
            ContactEntry.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${ContactEntry.COLUMN_LAST_MSG} DESC"
        )

        with(cursor) {
            while (moveToNext()) {
                contacts.add(toContact())
            }
        }
        cursor.close()
        return contacts
    }

    fun selectById(contactId: Int): Result<Contact> {
        val db = dbHelper.readableDatabase
        var result: Result<Contact>
        val cursor = db.query(
            ContactEntry.TABLE_NAME,
            null,
            "${BaseColumns._ID} = ?",
            arrayOf(contactId.toString()),
            null,
            null,
            null
        )

        result = if (cursor.moveToFirst()) {
            Result.success(cursor.toContact())
        } else {
            Result.failure(NoSuchElementException("No Contact found"))
        }

        cursor.close()
        return result
    }

    fun insert(contact: Contact) {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(ContactEntry.COLUMN_FIRST_NAME, contact.firstName)
            put(ContactEntry.COLUMN_LAST_NAME, contact.lastName)
            put(ContactEntry.COLUMN_PHONE_NUMBER, contact.phoneNumber)
            put(ContactEntry.COLUMN_PROFILE_PIC, contact.profilePicture)
            put(ContactEntry.COLUMN_LAST_MSG, contact.lastMsg)
            put(ContactEntry.COLUMN_CREATED_AT, contact.createdAt)
        }

        db.insert(ContactEntry.TABLE_NAME, null, values)
    }

    fun update(
        contactId: Int,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profilePicture: Int? = null,
        lastMsg: String? = null
    ) {
        val db = dbHelper.writableDatabase
        val values = ContentValues()

        firstName?.let { values.put(ContactEntry.COLUMN_FIRST_NAME, it) }
        lastName?.let { values.put(ContactEntry.COLUMN_LAST_NAME, it) }
        phoneNumber?.let { values.put(ContactEntry.COLUMN_PHONE_NUMBER, it) }
        profilePicture?.let { values.put(ContactEntry.COLUMN_PROFILE_PIC, it) }
        lastMsg?.let { values.put(ContactEntry.COLUMN_LAST_MSG, it) }

        db.update(
            ContactEntry.TABLE_NAME, values, "${BaseColumns._ID} = ?", arrayOf(contactId.toString())
        )
    }

    fun delete(contactId: Int) {
        val db = dbHelper.writableDatabase

        db.delete(ContactEntry.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(contactId.toString()))
    }
}

