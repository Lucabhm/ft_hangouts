package com.example.ft_hangouts.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.ft_hangouts.data.model.Contacts

@Entity(tableName = "calls")
data class Call (
    @PrimaryKey() @ColumnInfo("id") val id: Number,
    @ColumnInfo("contact") var contact: Contacts,
    @ColumnInfo("created_at") var createdAt: String
)
