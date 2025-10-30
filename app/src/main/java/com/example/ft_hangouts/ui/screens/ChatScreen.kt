package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ft_hangouts.data.model.Messages
import com.example.ft_hangouts.ui.components.MessageCard

@Composable
fun ChatScreen(modifier: Modifier = Modifier) {
    val messages = listOf(
        Messages(1, "Hallo was geht test das ist ein test", 0, "2022-05-02 14:23:57.123456"),
        Messages(2, "Hallo was geht", 1, "2022-05-02 14:23:57.123456"),
        Messages(3, "Hallo was geht", 0, "2022-05-02 14:23:57.123456"),
        Messages(4, "Hallo was geht lol was macht du da bist du krass oder was lol asdfasfd fdafsfd asfasfdasdf", 1, "2022-05-02 14:23:57.123456"),
        Messages(5, "Hallo was geht", 1, "2022-05-02 14:23:57.123456"),
        Messages(6, "Hallo was geht", 1, "2022-05-02 14:23:57.123456"),
        Messages(7, "Hallo was geht", 0, "2022-05-02 14:23:57.123456")
    )
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(items = messages, itemContent = { item -> MessageCard(messageInfo = item) })
    }
}
