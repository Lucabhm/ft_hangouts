package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.ft_hangouts.data.model.Messages
import com.example.ft_hangouts.ui.components.MessageCard

@Composable
fun MessagesScreen(modifier: Modifier = Modifier) {
    var textFieldValue by remember { mutableStateOf(TextFieldValue()) }
    val messages = listOf(
        Messages(1, "Hallo was geht test das ist ein test", 0, "2022-05-02 14:23:57.123456"),
        Messages(2, "Hallo was geht", 1, "2022-05-02 14:23:57.123456"),
        Messages(3, "Hallo was geht", 0, "2022-05-02 14:23:57.123456"),
        Messages(
            4,
            "Hallo was geht lol was macht du da bist du krass oder was lol asdfasfd fdafsfd asfasfdasdf",
            1,
            "2022-05-02 14:23:57.123456"
        ),
        Messages(5, "Hallo was geht", 1, "2022-05-02 14:23:57.123456"),
        Messages(6, "Hallo was geht", 1, "2022-05-02 14:23:57.123456"),
        Messages(7, "Hallo was geht", 0, "2022-05-02 14:23:57.123456")
    )
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LazyColumn(modifier = modifier.weight(1f)) {
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
