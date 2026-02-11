package com.example.ft_hangouts.data.local.dao

import com.example.ft_hangouts.data.local.entities.Call

@Dao
interface CallDao {
    @Query("SELECT * FROM calls")
    suspend fun selectAllCalls(): List<Call>

    @Query("SELECT * FROM calls WHERE id == :callId")
    suspend fun selectCallById(callId: Int): Call

    @Insert
    suspend fun insertCall(vararg call: Call)

    @Update
    suspend fun updateCall(vararg call: Call)

    @Delete
    suspend fun deleteCall(vararg call: Call)
}