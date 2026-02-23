package com.example.ft_hangouts.data.repository

import android.content.Context
import android.telephony.SmsManager
import android.util.Log

class SMSRepository(private val context: Context) {
    fun sendSms(phoneNumber: String, msg: String) {
        val smsManager = context.getSystemService(SmsManager::class.java)

        smsManager.sendTextMessage(phoneNumber, null, msg, null, null)
    }
}