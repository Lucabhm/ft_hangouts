package com.example.ft_hangouts.data.repository

import com.example.ft_hangouts.data.local.dao.ThemeColorDao
import com.example.ft_hangouts.data.model.ThemeColor
import com.example.ft_hangouts.data.model.UIResult

class ThemeRepository(private val themeColorDao: ThemeColorDao) {
    suspend fun getThemeColor(): UIResult<ThemeColor> {
        return themeColorDao.select().fold(onSuccess = { UIResult.Success(it) }, onFailure = {
            when (it) {
                is NoSuchElementException -> {
                    UIResult.NotFound(it.message ?: "")
                }

                else -> {
                    UIResult.DataBaseError
                }
            }
        })
    }

    suspend fun updateThemeColor(upColor: Long): UIResult<Int> {
        return themeColorDao.update(upColor)
            .fold(onSuccess = { UIResult.Success(it) }, onFailure = {
                UIResult.DataBaseError
            })
    }
}