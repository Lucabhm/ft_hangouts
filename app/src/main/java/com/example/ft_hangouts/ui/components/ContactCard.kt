package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.R

@Composable
fun ContactCard(modifier: Modifier = Modifier, contact: Contact, onClick: () -> Unit) {
    Card(
        modifier
            .fillMaxWidth()
            .height(100.dp).clickable(onClick = onClick)
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            var pic = contact.profilePicture
            if (pic == 0 || pic == null)
                pic = R.drawable.cr

            Image(
                painter = painterResource(id = pic),
                contentDescription = contact.firstName,
                modifier = modifier
                    .size(64.dp)
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
}
