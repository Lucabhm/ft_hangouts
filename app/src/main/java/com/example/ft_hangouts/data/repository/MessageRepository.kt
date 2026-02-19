package com.example.ft_hangouts.data.repository

import android.util.Log
import com.example.ft_hangouts.data.local.dao.MessageDao
import com.example.ft_hangouts.data.model.Message

class MessageRepository(private val messageDao: MessageDao) {
    fun getAllMessages(contactId: Long): UIResult<List<Message>> {
        return messageDao.selectAll(contactId).fold(onSuccess = { UIResult.Success(it) }, onFailure = {
            Log.d("test", "it = $it")
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(
                    it.message ?: ""
                )

                else -> UIResult.DataBaseError
            }
        })
    }

    fun createMessage(
        message: Message
    ): UIResult<Long> {
        return messageDao.insert(message).fold(
            onSuccess = { UIResult.Success(it) },
            onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(
                        it.message ?: ""
                    )

                    else -> UIResult.DataBaseError
                }
            })
    }

    fun deleteMessage(messageId: Long): UIResult<Int> {
        return messageDao.deleteById(messageId)
            .fold(onSuccess = { UIResult.Success(it) }, onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                    else -> UIResult.DataBaseError
                }
            })
    }
}