package com.example.ft_hangouts.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "contacts", indices = [Index(value = ["phone_number"], unique = true)])
data class Contact(
    @PrimaryKey(autoGenerate = true) @ColumnInfo("id") val id: Long = 0,
    @ColumnInfo("first_name") var firstName: String?,
    @ColumnInfo("last_name") var lastName: String?,
    @ColumnInfo("phone_number") var number: String,
    @ColumnInfo("profile_picture") var profilePicture: Int?
)
