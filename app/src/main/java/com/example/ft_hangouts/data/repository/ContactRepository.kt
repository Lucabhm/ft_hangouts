package com.example.ft_hangouts.data.repository

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteFullException
import com.example.ft_hangouts.data.local.dao.ContactDao
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.UIResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlin.fold

class ContactRepository(private val contactDao: ContactDao) {
    private val _contactUpdate = MutableSharedFlow<Unit>(replay = 1).apply {
        tryEmit(Unit)
    }

    fun getAllContacts(): Flow<UIResult<List<Contact>>> = _contactUpdate.map {
        contactDao.selectAll().fold(onSuccess = { UIResult.Success(it) }, onFailure = {
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(
                    it.message ?: ""
                )

                else -> UIResult.DataBaseError
            }
        })
    }

    fun getOwnContact(): UIResult<Contact> {
        return contactDao.getOwnContact().fold(onSuccess = { UIResult.Success(it) }, onFailure = {
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                else -> UIResult.DataBaseError
            }
        })
    }

    fun getContactByPhoneNumber(phoneNumber: String): UIResult<Contact> {
        return contactDao.selectByPhoneNumber(phoneNumber)
            .fold(onSuccess = {
                _contactUpdate.tryEmit(Unit)
                UIResult.Success(it)
            }, onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(
                        it.message ?: ""
                    )

                    else -> UIResult.DataBaseError
                }
            })
    }

    fun createContact(
        contact: Contact
    ): UIResult<Long> {
        return contactDao.insert(contact).fold(
            onSuccess = {
                _contactUpdate.tryEmit(Unit)
                UIResult.Success(it)
            },
            onFailure = {
                when (it) {
                    is NoSuchElementException -> {
                        UIResult.NotFound("Contact not found")
                    }
                    is SQLiteFullException -> {
                        UIResult.NotFound("")
                    }
                    is SQLiteConstraintException -> {
                        UIResult.NotFound("This Contact already exist with this phone number")
                    }
                    else -> UIResult.DataBaseError
                }
            })
    }

    fun updateContact(
        contactId: Long,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profilePicture: String? = null,
        lastMsg: Long? = null
    ): UIResult<Int> {
        return contactDao.updateById(
            contactId,
            firstName,
            lastName,
            phoneNumber,
            profilePicture,
            lastMsg
        ).fold(
            onSuccess = {
                _contactUpdate.tryEmit(Unit)
                UIResult.Success(it)
            },
            onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                    else -> UIResult.DataBaseError
                }
            })
    }

    fun deleteContact(contactId: Long): UIResult<Int> {
        return contactDao.deleteById(contactId)
            .fold(onSuccess = {
                _contactUpdate.tryEmit(Unit)
                UIResult.Success(it)
            }, onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                    else -> UIResult.DataBaseError
                }
            })
    }
}