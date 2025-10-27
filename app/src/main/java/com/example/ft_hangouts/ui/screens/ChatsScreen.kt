package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.Contacts

@Composable
fun ChatsScreen(modifier: Modifier = Modifier) {
    val contacts = listOf(
        Contacts("1", "Anna", 123456789, R.drawable.cr),
        Contacts("2", "Tom", 987654321, R.drawable.cr),
        Contacts("3", "Chris", 5551234, R.drawable.cr),
        Contacts("4", "Hallo", 5551234, R.drawable.cr),
        Contacts("5", "Test", 5551234, R.drawable.cr),
        Contacts("6", "LOL", 5551234, R.drawable.cr),
        Contacts("7", "Mama", 5551234, R.drawable.cr),
        Contacts("8", "What the hell", 5551234, R.drawable.cr),
        Contacts("9", "MOIN MOIN", 5551234, R.drawable.cr),
    )

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(items = contacts, itemContent = { item ->
            ContactCard(item)
        })
    }
}

@Composable
fun ContactCard(contact: Contacts, modifier: Modifier = Modifier) {
    Card(
        modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = contact.profilePicture),
                contentDescription = contact.name,
                modifier = modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = modifier.width(12.dp))
            Text(text = contact.name)
        }
    }
}
