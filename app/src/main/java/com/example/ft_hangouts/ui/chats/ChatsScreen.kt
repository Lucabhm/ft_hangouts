package com.example.ft_hangouts.ui.chats

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.Contacts
import com.example.ft_hangouts.ui.components.ContactCard

@Composable
fun ChatsScreen(navController: NavController, modifier: Modifier = Modifier, viewModel: ChatsViewModel = ) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(items = contacts, itemContent = { item ->
            ContactCard(item, navController)
        })
    }
}
