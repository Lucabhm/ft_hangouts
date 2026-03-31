package com.example.ft_hangouts.ui.components

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.NavResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    currentRoute: NavResult,
    goBack: () -> Unit,
    onAdd: () -> Unit
) {
    val showBack =
        currentRoute is NavResult.ChatScreen || currentRoute is NavResult.AddContactScreen || currentRoute is NavResult.UpdateContactScreen

    CenterAlignedTopAppBar(
        title = {
            if (currentRoute !is NavResult.ChatScreen)
                Text("ft_hangouts")
            else {
                val contact = currentRoute.contact

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (!contact.profilePicture.isNullOrEmpty()) {
                        Image(
                            bitmap = loadStringToBitmap(contact.profilePicture),
                            contentDescription = contact.firstName,
                            modifier = modifier
                                .size(64.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.cr),
                            contentDescription = contact.firstName,
                            modifier = modifier
                                .size(64.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
                    Spacer(modifier = modifier.width(12.dp))
                    val firstName = contact.firstName
                    val lastName = contact.lastName

                    if (!firstName.isNullOrBlank() || !lastName.isNullOrBlank()) {
                        if (!firstName.isNullOrBlank()) {
                            Text(text = firstName)
                            if (!lastName.isNullOrBlank())
                                Spacer(modifier = modifier.width(5.dp))
                        }
                        if (!lastName.isNullOrBlank())
                            Text(text = lastName)
                    } else {
                        Text(text = contact.phoneNumber)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = goBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        },
        actions = {
            if (currentRoute is NavResult.ChatScreen) {
                val contact = currentRoute.contact
                val context = LocalContext.current

                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_CALL).apply {
                        data = "tel:${contact.phoneNumber}".toUri()
                    }

                    context.startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Call,
                        contentDescription = "callIcon",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else if (currentRoute is NavResult.ContactsScreen) {
                IconButton(
                    onClick =
                        onAdd,
                    modifier = modifier
                        .size(48.dp)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "addContact",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    )
}
