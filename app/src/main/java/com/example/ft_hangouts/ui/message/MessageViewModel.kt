package com.example.ft_hangouts.ui.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.MessageRepository
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MessageViewModel(private val messageRepository: MessageRepository) : ViewModel() {
    private val _state = MutableStateFlow<UIResult<List<Message>>>(UIResult.Loading)
    val state: StateFlow<UIResult<List<Message>>> = _state

    fun loadMessages() {
        viewModelScope.launch {
            _state.value = messageRepository.getAllMessages()
        }
    }
}