package com.example.ft_hangouts.data.local.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.toContact
import com.example.ft_hangouts.data.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ContactDao(private val dbHelper: SQLiteOpenHelper) {
    suspend fun selectAll(): Result<List<Contact>> = withContext(Dispatchers.IO) {
        try {
            val db = dbHelper.readableDatabase
            val contacts = mutableListOf<Contact>()

            db.query(
                ContactEntry.TABLE_NAME,
                null,
                "${BaseColumns._ID} != ?",
                arrayOf("1"),
                null,
                null,
                "${ContactEntry.COLUMN_LAST_MSG} DESC"
            ).use { cursor ->
                while (cursor.moveToNext()) {
                    contacts.add(cursor.toContact())
                }
            }
            Log.d("test", "$contacts")
            Result.success(contacts)
        } catch (e: Exception) {
            Log.d("test", "$e")
            Result.failure(e)
        }
    }

    fun selectById(contactId: Long): Result<Contact> {
        return try {
            val db = dbHelper.readableDatabase

            db.query(
                ContactEntry.TABLE_NAME,
                null,
                "${BaseColumns._ID} = ?",
                arrayOf(contactId.toString()),
                null,
                null,
                null
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    Result.success(cursor.toContact())
                } else {
                    Result.failure(NoSuchElementException("No Contact found"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getOwnContact(): Result<Contact> = withContext(Dispatchers.IO) {
        try {
            val db = dbHelper.readableDatabase

            db.query(
                ContactEntry.TABLE_NAME,
                null,
                "${ContactEntry.COLUMN_PHONE_NUMBER} = ?",
                arrayOf("self"),
                null,
                null,
                null
            ).use { cursor ->
                if (cursor.moveToFirst()) {
                    Result.success(cursor.toContact())
                } else {
                    Result.failure(NoSuchElementException("No Contact found"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun selectByPhoneNumber(phoneNumber: String): Result<Contact> =
        withContext(Dispatchers.IO) {
            try {
                val db = dbHelper.readableDatabase

                db.query(
                    ContactEntry.TABLE_NAME,
                    null,
                    "${ContactEntry.COLUMN_PHONE_NUMBER} = ?",
                    arrayOf(phoneNumber),
                    null,
                    null,
                    null
                ).use { cursor ->
                    if (cursor.moveToFirst()) {
                        Result.success(cursor.toContact())
                    } else {
                        Result.failure(NoSuchElementException("No Contact found"))
                    }
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    suspend fun insert(contact: Contact): Result<Long> = withContext(Dispatchers.IO) {
        try {
            val db = dbHelper.writableDatabase
            val values = ContentValues().apply {
                put(ContactEntry.COLUMN_FIRST_NAME, contact.firstName)
                put(ContactEntry.COLUMN_LAST_NAME, contact.lastName)
                put(ContactEntry.COLUMN_PHONE_NUMBER, contact.phoneNumber)
                put(ContactEntry.COLUMN_PROFILE_PIC, contact.profilePicture)
                put(ContactEntry.COLUMN_LAST_MSG, contact.lastMsg)
                put(ContactEntry.COLUMN_CREATED_AT, contact.createdAt)
            }

            val id = db.insert(ContactEntry.TABLE_NAME, null, values)
            if (id == -1L) Result.failure(Exception())
            else Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateById(
        contactId: Long,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profilePicture: String? = null,
        lastMsg: Long? = null
    ): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val db = dbHelper.writableDatabase
            val values = ContentValues()

            firstName?.let { values.put(ContactEntry.COLUMN_FIRST_NAME, it) }
            lastName?.let { values.put(ContactEntry.COLUMN_LAST_NAME, it) }
            phoneNumber?.let { values.put(ContactEntry.COLUMN_PHONE_NUMBER, it) }
            profilePicture?.let { values.put(ContactEntry.COLUMN_PROFILE_PIC, it) }
            lastMsg?.let { values.put(ContactEntry.COLUMN_LAST_MSG, it) }

            val count = db.update(
                ContactEntry.TABLE_NAME,
                values,
                "${BaseColumns._ID} = ?",
                arrayOf(contactId.toString())
            )

            if (count == 0) Result.failure(NoSuchElementException("Contact not found"))
            else Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteById(contactId: Long): Result<Int> = withContext(Dispatchers.IO) {
        try {
            val db = dbHelper.writableDatabase

            val count = db.delete(
                ContactEntry.TABLE_NAME, "${BaseColumns._ID} = ?", arrayOf(contactId.toString())
            )

            if (count == 0) Result.failure(NoSuchElementException("Contact not found"))
            else Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

