package com.example.ft_hangouts.data.repository

import android.app.PendingIntent
import android.content.Context
import android.telephony.SmsManager
import android.util.Log
import java.lang.Exception

class SMSRepository(private val context: Context) {
    fun sendSms(phoneNumber: String, msg: String) {
        try {
            val smsManager = context.getSystemService(SmsManager::class.java)

            smsManager.sendTextMessage(phoneNumber, null, msg, null, null)
            Log.d("test", "Message sent")
        } catch (e: Exception) {
            Log.d("test", "error ${e.message}")
        }
    }
}