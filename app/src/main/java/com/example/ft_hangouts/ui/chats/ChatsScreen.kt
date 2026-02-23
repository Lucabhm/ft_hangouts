package com.example.ft_hangouts.ui.chats

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.repository.UIResult
import com.example.ft_hangouts.ui.components.ContactCard
import com.example.ft_hangouts.data.model.Contact

@Composable
fun ChatsScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatsViewModel,
    onClick: (Contact) -> Unit
) {
    val state by remember {
        viewModel.loadContacts()
    }.collectAsState()

    when (state) {
        is UIResult.Loading -> {
            Text("Loading")
        }

        is UIResult.Success -> {
            val contacts = (state as UIResult.Success<List<Contact>>).data
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(5.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(items = contacts, itemContent = { item ->
                    ContactCard(contact = item, onClick = { onClick(item) })
                })
            }
        }

        is UIResult.NotFound -> {
            Text("NotFound")
        }

        is UIResult.DataBaseError -> {
            Text("DB Error")
        }
    }
}
