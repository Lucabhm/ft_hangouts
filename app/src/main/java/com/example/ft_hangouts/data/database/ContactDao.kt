package com.example.ft_hangouts.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

@Dao
interface ContactDao {
    @Insert
    fun insertContact(vararg contacts: Contact)

    @Update
    fun updateContact(vararg contacts: Contact)

    @Delete
    fun deleteContact(vararg contacts: Contact)
}