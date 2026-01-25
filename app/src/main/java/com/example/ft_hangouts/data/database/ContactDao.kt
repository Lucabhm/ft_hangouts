package com.example.ft_hangouts.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun selectAllContacts(): List<Contact>

    @Query("SELECT * FROM contacts WHERE id == :userId")
    fun selectContactById(userId: Int): Contact

    @Insert
    fun insertContact(vararg contacts: Contact)

    @Update
    fun updateContact(vararg contacts: Contact)

    @Delete
    fun deleteContact(vararg contacts: Contact)
}