package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.ui.navigation.NavResult

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(modifier: Modifier = Modifier, currentRoute: NavResult, goBack: () -> Unit) {
   val showBack = currentRoute is NavResult.ChatScreen || currentRoute is NavResult.AddContactScreen

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
                    var pic = contact.profilePicture
                    if (pic == null || pic == 0)
                        pic = R.drawable.cr

                    Image(
                        painter = painterResource(id = pic),
                        contentDescription = contact.firstName,
                        modifier = modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = modifier.width(12.dp))
                    val firstName = contact.firstName
                    val lastName = contact.lastName

                    if ((firstName != null && !firstName.isBlank()) || (lastName != null && !lastName.isBlank())) {
                        if (firstName != null && !firstName.isBlank()) {
                            Text(text = firstName)
                            if (lastName != null && !lastName.isBlank())
                                Spacer(modifier = modifier.width(5.dp))
                        }
                        if (lastName != null && !lastName.isBlank())
                            Text(text = lastName)
                    } else {
                        Text(text = contact.phoneNumber)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = goBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back"
                    )
                }
            }
        },
        actions = {
            if (currentRoute is NavResult.ChatScreen) {
                IconButton(onClick = {}) { // TODO add phone logic
                    Icon(imageVector = Icons.Default.Call, contentDescription = "callIcon")
                }
            }
        }
    )
}
