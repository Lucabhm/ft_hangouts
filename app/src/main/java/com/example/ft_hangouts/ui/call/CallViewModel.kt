package com.example.ft_hangouts.ui.call

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.Call
import com.example.ft_hangouts.data.repository.CallRepository
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CallViewModel(private val callRepository: CallRepository) : ViewModel() {
    private val _state = MutableStateFlow<UIResult<List<Call>>>(UIResult.Loading)
    val state: StateFlow<UIResult<List<Call>>> = _state

    fun loadCalls() {
        viewModelScope.launch {
            _state.value = callRepository.getAllCalls()
        }
    }
}