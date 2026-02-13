package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.model.Message

@Composable
fun MessageCard(modifier: Modifier = Modifier, messageInfo: Message) {
    Row(
        modifier = modifier.fillMaxWidth().padding(5.dp),
        horizontalArrangement = if (messageInfo.fromId != 0L) Arrangement.Start else Arrangement.End
    ) {
        Card() {
            Column(modifier = Modifier
                .padding(5.dp)
                .widthIn(max = 260.dp)) {
                Text(text = messageInfo.message)
                Text(text = "12:30", Modifier.align(Alignment.End))
            }
        }
    }
}