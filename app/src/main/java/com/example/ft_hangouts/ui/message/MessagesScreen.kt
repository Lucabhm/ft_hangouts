package com.example.ft_hangouts.ui.message

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.ft_hangouts.data.model.Message
import com.example.ft_hangouts.data.repository.UIResult
import com.example.ft_hangouts.ui.components.MessageCard

@Composable
fun MessagesScreen(modifier: Modifier = Modifier, viewModel: MessageViewModel) {
    LaunchedEffect(Unit) {
        viewModel.loadMessages()
    }

    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    val state by viewModel.state.collectAsState()

    when (state) {
        is UIResult.Loading -> {}
        is UIResult.Success -> {
            val messages = (state as UIResult.Success<List<Message>>).data

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
                TextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    modifier = modifier
                        .fillMaxWidth()
                )
            }
        }

        is UIResult.NotFound -> {}
        else -> {}
    }
}
