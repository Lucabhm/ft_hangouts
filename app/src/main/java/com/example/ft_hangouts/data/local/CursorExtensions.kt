package com.example.ft_hangouts.data.local

import android.database.Cursor
import android.provider.BaseColumns
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.model.Contact

fun Cursor.toContact() = Contact(
    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
    firstName = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_FIRST_NAME)),
    lastName = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_LAST_NAME)),
    phoneNumber = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_PHONE_NUMBER)),
    profilePicture = getInt(getColumnIndexOrThrow(ContactEntry.COLUMN_PROFILE_PIC)),
    lastMsg = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_LAST_MSG)),
    createdAt = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_CREATED_AT))
)