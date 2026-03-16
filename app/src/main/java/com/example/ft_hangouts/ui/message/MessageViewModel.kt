package com.example.ft_hangouts.ui.message

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.ContactRepository
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.data.repository.SMSRepository
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageViewModel(
    private val messageRepository: MessageRepository,
    private val contactRepository: ContactRepository,
    private val smsRepository: SMSRepository
) : ViewModel() {
    fun loadMessages(contact: Contact): StateFlow<UIResult<List<Message>>> {
        return flow {
            emit(UIResult.Loading)

            when (val state = contactRepository.getOwnContact()) {
                is UIResult.Loading -> {
                    emit(state)
                }

                is UIResult.Success -> {
                    val ownContact = state.data

                    emitAll(messageRepository.getAllMessages(ownContact.id!!, contact.id!!))
                }

                is UIResult.NotFound -> {
                    emit(state)
                }

                is UIResult.DataBaseError -> {
                    emit(state)
                }
            }

        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = UIResult.Loading
        )
    }

    fun sendMessage(input: String, sendTo: Contact) {
        if (sendTo.id == null)
            return
        viewModelScope.launch {
            when (val state = contactRepository.getOwnContact()) {
                is UIResult.Loading -> {}
                is UIResult.Success -> {
                    val ownContact = state.data
                    val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                    val current = sdf.format(Date())
                    if (!input.isBlank()) {
                        Log.d("test", input)
                        val inputCheck = input.trim()
                        val message =
                            Message(
                                message = inputCheck,
                                fromId = ownContact.id!!,
                                sendToId = sendTo.id,
                                createdAt = current
                            )

                        smsRepository.sendSms(sendTo.phoneNumber, inputCheck)
                        messageRepository.createMessage(message)
                        contactRepository.updateContact(sendTo.id, null, null, null, null, current)
                    }
                }

                is UIResult.NotFound -> {}
                is UIResult.DataBaseError -> {}
            }
        }
    }
}