package com.example.ft_hangouts.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 1, entities = [Contact::class, Message::class, Call::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun getContactDao(): ContactDao

    abstract fun getMessageDao(): MessageDao

    abstract fun getCallDao(): CallDao
}