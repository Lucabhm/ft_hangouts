package com.example.ft_hangouts.ui.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.model.UIResult
import com.example.ft_hangouts.ui.components.MessageCard

@Composable
fun MessagesScreen(modifier: Modifier = Modifier, viewModel: MessageViewModel, contact: Contact) {
    val state by remember(contact) {
        viewModel.loadMessages(contact)
    }.collectAsState()

    when (state) {
        is UIResult.Loading -> {
            Text("Loading", modifier = modifier.fillMaxWidth())
        }

        is UIResult.Success -> {
            val messages = (state as UIResult.Success<List<Message>>).data
            val input = remember { mutableStateOf("") }

            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                LazyColumn(modifier = Modifier.weight(1f), reverseLayout = true)
                {
                    items(messages) { item -> MessageCard(messageInfo = item, contact = contact) }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextField(
                        value = input.value,
                        onValueChange = { input.value = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        placeholder = { Text(stringResource(R.string.message_input)) },
                        maxLines = 4,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Send,
                            keyboardType = KeyboardType.Text,
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                viewModel.sendMessage(input.value, contact)
                                input.value = ""
                            }
                        )
                    )

                    IconButton(
                        onClick = {
                            viewModel.sendMessage(input.value, contact)
                            input.value = ""
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            }
        }

        is UIResult.NotFound -> {
            Text("Not Found", modifier = modifier.fillMaxWidth())
        }

        is UIResult.DataBaseError -> {
            Text("DB Error", modifier = modifier.fillMaxWidth())
        }
    }
}
