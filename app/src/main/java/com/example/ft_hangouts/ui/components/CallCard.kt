package com.example.ft_hangouts.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import java.text.SimpleDateFormat
import java.util.Locale
import com.example.ft_hangouts.data.model.Call
import com.example.ft_hangouts.R

@Composable
fun CallCard(call: Call, modifier: Modifier = Modifier) {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd. MMM yyyy", Locale.getDefault())

    val date = inputFormat.parse(call.createdAt)
    val formatted = if (date != null) {
        outputFormat.format(date)
    } else {
        "Lost"
    }
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
                painter = painterResource(id = call.contact?.profilePicture ?: R.drawable.cr),
                contentDescription = call.contact?.firstName,
                modifier = modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = modifier.fillMaxSize().padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = call.contact?.firstName ?: "")
                Spacer(modifier = modifier.weight(1f))
                Text(text = formatted)
            }
        }
    }
}
