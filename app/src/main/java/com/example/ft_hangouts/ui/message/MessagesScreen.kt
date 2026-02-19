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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.UIResult
import com.example.ft_hangouts.ui.components.MessageCard

@Composable
fun MessagesScreen(modifier: Modifier = Modifier, viewModel: MessageViewModel, contact: Contact) {
    LaunchedEffect(Unit) {
        viewModel.loadMessages(contact)
    }

    val state by viewModel.state.collectAsState()
    val msgState by viewModel.messageState.collectAsState()

    when (msgState) {
        is UIResult.Loading -> {}
        is UIResult.Success -> {
            viewModel.loadMessages(contact)
        }
        is UIResult.NotFound -> {}
        is UIResult.DataBaseError -> {}
    }

    when (state) {
        is UIResult.Loading -> {
            Text("Loading")
        }

        is UIResult.Success -> {
            val messages = (state as UIResult.Success<List<Message>>).data
            val input = remember { mutableStateOf("") }

            Column(
                modifier = modifier
                    .fillMaxSize()
            ) {
                LazyColumn(
                    modifier = modifier
                        .weight(1f)
                ) {
                    items(messages) { item -> MessageCard(messageInfo = item) }
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
                        placeholder = { Text("Type a message") },
                        maxLines = 4,
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Send,
                            keyboardType = KeyboardType.Text,
                        ),
                        keyboardActions = KeyboardActions(
                            onSend = {
                                if (contact.id != null)
                                    viewModel.sendMessage(input.value, contact.id)
                                input.value = ""
                            }
                        )
                    )

                    IconButton(
                        onClick = {
                            if (contact.id != null)
                                viewModel.sendMessage(input.value, contact.id)
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
            Text("Not Found")
        }

        is UIResult.DataBaseError -> {
            Text("DataBaseError")
        }
    }
}
