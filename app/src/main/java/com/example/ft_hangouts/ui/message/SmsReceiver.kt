package com.example.ft_hangouts.ui.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.ft_hangouts.FtHangouts
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.UIResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SmsReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val app = context?.applicationContext as FtHangouts
        val contactRepository = app.container.contactRepo
        val messageRepository = app.container.messageRepo

        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
            val info = messages.firstOrNull()

            if (info != null) {
                val phoneNumber = info.originatingAddress

                if (phoneNumber != null) {
                    val check = contactRepository.getContactByPhoneNumber(phoneNumber)

                    when (check) {
                        is UIResult.Loading -> {}
                        is UIResult.Success<Contact> -> {
                            val contact = check.data

                            for (sms in messages) {
                                val date = Date(sms.timestampMillis)
                                val format =
                                    SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                                val formattedDate = format.format(date)

                                messageRepository.createMessage(
                                    Message(
                                        null,
                                        sms.messageBody,
                                        contact.id!!,
                                        0,
                                        formattedDate
                                    )
                                )
                            }
                        }

                        is UIResult.NotFound -> {
                            val sdf = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
                            val current = sdf.format(Date())

                            val contactId = contactRepository.createContact(
                                Contact(
                                    null,
                                    null,
                                    null,
                                    phoneNumber,
                                    null,
                                    null,
                                    current
                                )
                            )

                            when (contactId) {
                                is UIResult.Loading -> {}
                                is UIResult.Success -> {
                                    val id = contactId.data

                                    for (sms in messages) {
                                        val date = Date(sms.timestampMillis)
                                        val format =
                                            SimpleDateFormat(
                                                "dd.MM.yyyy HH:mm:ss",
                                                Locale.getDefault()
                                            )
                                        val formattedDate = format.format(date)

                                        messageRepository.createMessage(
                                            Message(
                                                null,
                                                sms.messageBody,
                                                id,
                                                0,
                                                formattedDate
                                            )
                                        )
                                    }
                                }

                                is UIResult.NotFound -> {}
                                is UIResult.DataBaseError -> {}
                            }

                        }

                        is UIResult.DataBaseError -> {}
                    }
                }
            }
        }
    }
}