package com.example.ft_hangouts.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class SettingsViewModel() : ViewModel() {
    private var _color by mutableStateOf(Color.Black)
    val color: Color get() = _color

    fun changeThemeColor(color: Color) {
        _color = color
    }
}