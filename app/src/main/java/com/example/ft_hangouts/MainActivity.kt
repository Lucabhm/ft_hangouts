package com.example.ft_hangouts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.ui.theme.Ft_hangoutsTheme
import model.Calls
import model.Contacts

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ft_hangoutsTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar() },
                    topBar = { TopBar() }
                ) { innerPadding ->
                    CallScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        title = { "ft_hangouts" },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun BottomBar() {
    val items = listOf("chats", "calls", "settings")
    var currItem by remember { mutableIntStateOf(0) }
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = currItem == index,
                onClick = { currItem = index },
                icon = {
                    when (item) {
                        "chats" -> Icon(Icons.Default.AccountBox, contentDescription = item)
                        "calls" -> Icon(Icons.Default.Call, contentDescription = item)
                        "settings" -> Icon(Icons.Default.Settings, contentDescription = item)
                    }
                })
        }
    }
}

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

@Composable
fun CallScreen(modifier: Modifier = Modifier) {
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
    val calls = listOf(
        Calls("1", contacts[0], "02-05-2022"),
        Calls("2", contacts[1], "02-05-2022"),
        Calls("3", contacts[2], "02-05-2022"),
        Calls("3", contacts[3], "02-05-2022"),
    )
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(
            items = calls,
            itemContent = { item -> CallCard(item) })
    }
}

@Composable
fun CallCard(call: Calls, modifier: Modifier = Modifier) {
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
                painter = painterResource(id = call.contact.profilePicture),
                contentDescription = call.contact.name,
                modifier = modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = modifier.width(12.dp))
            Text(text = call.contact.name)
            Spacer(modifier = modifier.width(12.dp))
            Text(text = call.createdAt)
        }
    }
}