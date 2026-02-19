package com.example.ft_hangouts.ui.navigation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.NavResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class NavViewModel : ViewModel() {
    private val _backStack = MutableStateFlow<List<NavResult>>(listOf(NavResult.ContactsScreen))

    val currentScreen: StateFlow<NavResult> = _backStack.map { it.last() }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly, _backStack.value.last()
    )


    fun navigateTo(screen: NavResult) {
        _backStack.value = _backStack.value + screen
    }

    fun goBack() {
        if (_backStack.value.size > 1)
            _backStack.value = _backStack.value.dropLast(1)
    }

    fun changeStack(mainScreen: NavResult) {
        Log.d("test", "hallo $mainScreen")
        _backStack.update { listOf(mainScreen) }
    }

}