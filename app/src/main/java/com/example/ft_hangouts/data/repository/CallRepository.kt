package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.local.dao.CallDao
import com.example.ft_hangouts.data.model.Call

class CallRepository(private val callDao: CallDao) {
    fun getAllCalls(): UIResult<List<Call>> {
        return callDao.selectAll().fold(onSuccess = { UIResult.Success(it) }, onFailure = {
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(
                    it.message ?: ""
                )

                else -> UIResult.DataBaseError
            }
        })
    }

//    fun createCall(
//        call: Call
//    ): UIResult<Long> {
//        return callDao.insert(call).fold(
//            onSuccess = { UIResult.Success(it) },
//            onFailure = {
//                when (it) {
//                    is NoSuchElementException -> UIResult.NotFound(
//                        it.message ?: ""
//                    )
//
//                    else -> UIResult.DataBaseError
//                }
//            })
//    }

    fun deleteCall(callId: Long): UIResult<Int> {
        return callDao.deleteById(callId)
            .fold(onSuccess = { UIResult.Success(it) }, onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                    else -> UIResult.DataBaseError
                }
            })
    }
}