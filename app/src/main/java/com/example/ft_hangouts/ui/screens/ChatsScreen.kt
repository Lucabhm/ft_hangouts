package com.example.ft_hangouts.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.Contacts
import com.example.ft_hangouts.ui.components.AddContactButton
import com.example.ft_hangouts.ui.components.ContactCard

@Composable
fun ChatsScreen(navController: NavController, modifier: Modifier = Modifier) {
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
            ContactCard(item, navController)
        })
    }
}
