package com.example.ft_hangouts.data.local

import android.database.Cursor
import android.provider.BaseColumns
import com.example.ft_hangouts.data.local.ContactContract.ContactEntry
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Call
import com.example.ft_hangouts.data.local.ContactContract.CallEntry
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.local.ContactContract.MessageEntry

fun Cursor.toContact() = Contact(
    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
    firstName = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_FIRST_NAME)),
    lastName = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_LAST_NAME)),
    phoneNumber = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_PHONE_NUMBER)),
    profilePicture = getInt(getColumnIndexOrThrow(ContactEntry.COLUMN_PROFILE_PIC)),
    lastMsg = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_LAST_MSG)),
    createdAt = getString(getColumnIndexOrThrow(ContactEntry.COLUMN_CREATED_AT))
)

fun Cursor.toCall() = Call(
    id = getLong(getColumnIndexOrThrow("call_id")),
    contact = Contact(
        id = getLong(getColumnIndexOrThrow("contact_id")),
        firstName = getString(getColumnIndexOrThrow("first_name")),
        lastName = getString(getColumnIndexOrThrow("last_name")),
        phoneNumber = getString(getColumnIndexOrThrow("phone_number")),
        profilePicture = getInt(getColumnIndexOrThrow("profile_picture")),
        lastMsg = getString(getColumnIndexOrThrow("last_msg")),
        createdAt = getString(getColumnIndexOrThrow("created_at"))
    ),
    createdAt = getString(getColumnIndexOrThrow("call_created_at"))
)

fun Cursor.toMessage() = Message(
    id = getLong(getColumnIndexOrThrow(BaseColumns._ID)),
    message = getString(getColumnIndexOrThrow(MessageEntry.COLUMN_MESSAGE)),
    fromId = getLong(getColumnIndexOrThrow(MessageEntry.COLUMN_FROM_ID)),
    sendToId = getLong(getColumnIndexOrThrow(MessageEntry.COLUMN_SEND_TO_ID)),
    createdAt = getString(getColumnIndexOrThrow(MessageEntry.COLUMN_CREATED_AT))
)
