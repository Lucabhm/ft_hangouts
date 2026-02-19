package com.example.ft_hangouts.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.data.repository.SMSRepository
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MessageViewModel(private val messageRepository: MessageRepository, private val smsRepository: SMSRepository) : ViewModel() {
    private val _state = MutableStateFlow<UIResult<List<Message>>>(UIResult.Loading)
    private val _message = MutableStateFlow<UIResult<Long>>(UIResult.Loading)
    val state: StateFlow<UIResult<List<Message>>> = _state
    val messageState: StateFlow<UIResult<Long>> = _message

    fun loadMessages(contact: Contact) {
        viewModelScope.launch {
            if (contact.id != null)
                _state.value = messageRepository.getAllMessages(contact.id)
        }
    }

    fun sendMessage(input: String, sendTo: Contact) {
        if (sendTo.id == null)
            return
        viewModelScope.launch {
            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            val current = sdf.format(Date())
            val message = Message(message = input, fromId = 0L, sendToId = sendTo.id, createdAt = current)

            smsRepository.sendSms(sendTo.phoneNumber, input)

            _message.value = messageRepository.createMessage(message)
        }
    }

}