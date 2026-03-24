package com.example.ft_hangouts.data.local

import android.database.Cursor
import android.provider.BaseColumns
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.local.ContactContract.MessageEntry
import com.example.ft_hangouts.data.local.ContactContract.ThemeColorEntry
import com.example.ft_hangouts.data.model.ThemeColor

fun Cursor.toContact() = Contact(
    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
    firstName = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_FIRST_NAME)),
    lastName = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_LAST_NAME)),
    phoneNumber = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_PHONE_NUMBER)),
    profilePicture = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_PROFILE_PIC)),
    lastMsg = getLong(getColumnIndexOrThrow(ContactEntry.COLUMN_LAST_MSG)),
    createdAt = getLong(getColumnIndexOrThrow(ContactEntry.COLUMN_CREATED_AT))
)

fun Cursor.toMessage() = Message(
    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
    message = getString(getColumnIndexOrThrow(MessageEntry.COLUMN_MESSAGE)),
    fromId = getLong(getColumnIndexOrThrow(MessageEntry.COLUMN_FROM_ID)),
    sendToId = getLong(getColumnIndexOrThrow(MessageEntry.COLUMN_SEND_TO_ID)),
    createdAt = getLong(getColumnIndexOrThrow(MessageEntry.COLUMN_CREATED_AT))
)

fun Cursor.toTheme() = ThemeColor(
    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)), themeColor = getLong(
        getColumnIndexOrThrow(
            ThemeColorEntry.COLUMN_COLOR
        )
    )
)