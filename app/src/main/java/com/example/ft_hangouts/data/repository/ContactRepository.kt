package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.local.dao.ContactDao
import com.example.ft_hangouts.data.model.Contact
import kotlin.fold

class ContactRepository(private val contactDao: ContactDao) {
    fun getAllContacts(): UIResult<List<Contact>> {
        return contactDao.selectAll().fold(onSuccess = { UIResult.Success(it) }, onFailure = {
            when (it) {
                is NoSuchElementException -> UIResult.NotFound(
                    it.message ?: ""
                )

                else -> UIResult.DataBaseError
            }
        })
    }

    fun getContactById(contactId: Long): UIResult<Contact> {
        return contactDao.selectById(contactId)
            .fold(onSuccess = { UIResult.Success(it) }, onFailure = {
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

    fun updateContact(
        contactId: Long,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profilePicture: Int? = null,
        lastMsg: String? = null
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
            .fold(onSuccess = { UIResult.Success(it) }, onFailure = {
                when (it) {
                    is NoSuchElementException -> UIResult.NotFound(it.message ?: "")
                    else -> UIResult.DataBaseError
                }
            })
    }
}