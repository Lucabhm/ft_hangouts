package com.example.ft_hangouts.ui.message

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
        return messageRepository.getAllMessages(contact.id!!).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UIResult.Loading
        )
    }

    fun sendMessage(input: String, sendTo: Contact) {
        if (sendTo.id == null)
            return
        viewModelScope.launch {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            val current = sdf.format(Date())
            val message =
                Message(message = input, fromId = 0L, sendToId = sendTo.id, createdAt = current)

            smsRepository.sendSms(sendTo.phoneNumber, input)
            messageRepository.createMessage(message)
            contactRepository.updateContact(sendTo.id, null, null, null, null, current)
        }
    }
}