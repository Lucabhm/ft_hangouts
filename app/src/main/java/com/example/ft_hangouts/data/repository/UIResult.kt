package com.example.ft_hangouts.data.repository

sealed class UIResult<out T> {
    object Loading : UIResult<Nothing>()
    data class Success<T>(val data: T) : UIResult<T>()
    data class NotFound(val msg: String) : UIResult<Nothing>()
    object DataBaseError : UIResult<Nothing>()
}