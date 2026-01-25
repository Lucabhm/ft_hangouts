package com.example.ft_hangouts.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CallDao {
    @Query("SELECT * FROM calls")
    fun selectAllCalls(): List<Call>

    @Query("SELECT * FROM calls WHERE id == :callId")
    fun selectCallById(callId: Int): Call

    @Insert
    fun insertCall(vararg call: Call)

    @Update
    fun updateCall(vararg call: Call)

    @Delete
    fun deleteCall(vararg call: Call)
}