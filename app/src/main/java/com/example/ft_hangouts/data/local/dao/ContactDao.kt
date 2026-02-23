package com.example.ft_hangouts.data.local.dao

import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.local.toContact
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.MutableSharedFlow

class ContactDao(private val dbHelper: SQLiteOpenHelper) {
    fun selectAll(): Result<List<Contact>> {
        return try {
            val db = dbHelper.readableDatabase
            val contacts = mutableListOf<Contact>()

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
                    contacts.add(cursor.toContact())
                }
            }
            Result.success(contacts)
        } catch (e: Exception) {
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

    fun selectByPhoneNumber(phoneNumber: String): Result<Contact> {
        return try {
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

    fun insert(contact: Contact): Result<Long> {
        return try {
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
            if (id == -1L)
                Result.failure(Exception("Contact insert failed"))
            else
                Result.success(id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun updateById(
        contactId: Long,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profilePicture: Int? = null,
        lastMsg: String? = null
    ): Result<Int> {
        return try {
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

            if (count == 0)
                Result.failure(NoSuchElementException("Contact not found"))
            else
                Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun deleteById(contactId: Long): Result<Int> {
        return try {
            val db = dbHelper.writableDatabase

            val count = db.delete(
                ContactEntry.TABLE_NAME,
                "${BaseColumns._ID} = ?",
                arrayOf(contactId.toString())
            )

            if (count == 0)
                Result.failure(NoSuchElementException("Contact not found"))
            else
                Result.success(count)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

