package com.example.ft_hangouts.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact (
    @ColumnInfo("id") val id: Number,
    @ColumnInfo("first_name") var firstName: String?,
    @ColumnInfo("last_name") var lastName: String?,
    @PrimaryKey() @ColumnInfo("phone_number") var number: Int,
    @ColumnInfo("profile_picture") var profilePicture: Int?
)
