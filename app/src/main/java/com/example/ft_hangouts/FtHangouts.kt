package com.example.ft_hangouts

import android.app.Application
import com.example.ft_hangouts.data.local.AppContainer

class FtHangouts : Application() {
    lateinit var container: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}