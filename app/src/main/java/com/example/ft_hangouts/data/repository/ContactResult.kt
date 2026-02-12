package com.example.ft_hangouts.data.repository

sealed class UIResult<out T> {
    data class Success<T>(val contact: T) : UIResult<T>()
    data class NotFound(val msg: String) : UIResult<Nothing>()
    object DataBaseError : UIResult<Nothing>()
}