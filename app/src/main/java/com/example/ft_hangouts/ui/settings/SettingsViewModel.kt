package com.example.ft_hangouts.ui.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toColorLong
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ft_hangouts.data.model.UIResult
import com.example.ft_hangouts.data.repository.ThemeRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val themeRepository: ThemeRepository) : ViewModel() {
    private var _color by mutableStateOf(Color.Black)
    val color: Color get() = _color

    fun loadThemeColor() {
        viewModelScope.launch {
            val color = themeRepository.getThemeColor()

            if (color is UIResult.Success) {
                _color = Color(color.data.themeColor.toULong())
            }
        }
    }

    fun changeThemeColor(color: Color) {
        viewModelScope.launch {
            _color = color
            themeRepository.updateThemeColor(color.toColorLong())
        }
    }
}