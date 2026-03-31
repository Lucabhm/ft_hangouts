package com.example.ft_hangouts.ui.message

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.ft_hangouts.FtHangouts
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.model.UIResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {
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

                val ownId = ownContact.data.id
                val state = contactRepository.getContactByPhoneNumber(phoneNumber)

                val contactId = when (state) {
                    is UIResult.Success -> {
                        val contact = state.data
                        val current = System.currentTimeMillis()

                        contactRepository.updateContact(
                            contact.id,
                            null,
                            null,
                            null,
                            null,
                            null,
                            current
                        )
                        contact.id
                    }

                    is UIResult.NotFound -> {
                        val contactId = contactRepository.createContact(
                            null,
                            null,
                            phoneNumber,
                            null,
                        )

                        if (contactId is UIResult.Success) contactId.data else return@launch
                    }

                    else -> return@launch
                }

                val current = System.currentTimeMillis()

                for (sms in messages) {
                    if (!sms.messageBody.isBlank()) {
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
                }

            } finally {
                pendingResult.finish()
            }
        }

    }
}
