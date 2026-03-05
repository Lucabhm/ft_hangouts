package com.example.ft_hangouts.ui.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.ft_hangouts.FtHangouts
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.UIResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SmsReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val app = context?.applicationContext as FtHangouts
        val contactRepository = app.container.contactRepo
        val messageRepository = app.container.messageRepo

        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
                val info = messages.firstOrNull() ?: return@launch
                val phoneNumber = info.originatingAddress ?: return@launch
                val ownContact = contactRepository.getOwnContact()

                if (ownContact !is UIResult.Success) return@launch

                val ownId = ownContact.data.id!!
                val state = contactRepository.getContactByPhoneNumber(phoneNumber)

                val contactId = when (state) {
                    is UIResult.Success -> {
                        val contact = state.data
                        val sdf = SimpleDateFormat(
                            "dd.MM.yyyy HH:mm:ss",
                            Locale.getDefault()
                        )
                        val current = sdf.format(Date())

                        contactRepository.updateContact(
                            contact.id!!,
                            null,
                            null,
                            null,
                            null,
                            current
                        )
                        contact.id
                    }

                    is UIResult.NotFound -> {
                        val sdf = SimpleDateFormat(
                            "dd.MM.yyyy HH:mm:ss",
                            Locale.getDefault()
                        )
                        val current = sdf.format(Date())
                        val contactId = contactRepository.createContact(
                            Contact(
                                null,
                                null,
                                null,
                                phoneNumber,
                                null,
                                current,
                                current
                            )
                        )
                        if (contactId is UIResult.Success) contactId.data else return@launch
                    }

                    else -> return@launch
                }

                val sdf = SimpleDateFormat(
                    "dd.MM.yyyy HH:mm:ss",
                    Locale.getDefault()
                )
                val current = sdf.format(Date())

                for (sms in messages) {
                    messageRepository.createMessage(
                        Message(
                            null,
                            sms.messageBody,
                            contactId,
                            ownId,
                            current
                        )
                    )
                }

            } catch (e: Exception) {

            } finally {
                pendingResult.finish()
            }
        }

    }
}
