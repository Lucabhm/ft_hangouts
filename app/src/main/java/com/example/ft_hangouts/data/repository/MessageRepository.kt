package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.local.dao.MessageDao
import com.example.ft_hangouts.data.model.Message
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

class MessageRepository(private val messageDao: MessageDao) {
    private val _messageUpdate = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    fun getAllMessages(contactId: Long): Flow<UIResult<List<Message>>> = _messageUpdate.map {
        messageDao.selectAll(contactId)
            .fold(onSuccess = { UIResult.Success(it) }, onFailure = {
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
            onSuccess = {
                _messageUpdate.tryEmit(Unit)
                UIResult.Success(it)
            },
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