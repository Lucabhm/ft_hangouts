package com.example.ft_hangouts.ui.updateContact

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.model.Contact

@Composable
fun UpdateContactScreen(modifier: Modifier = Modifier, viewModel: UpdateContactViewModel, contact: Contact) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(120.dp)
                .border(
                    width = 4.dp,
                    color = MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable(
                    onClick = {}, // TODO add Pic
                ),
        ) { Icon(Icons.Default.Add, "addPic") }

        Text(text = "Add a Picture")

        Column {
            Text(text = "Enter a First Name")
            OutlinedTextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.firstName = it },
                label = { Text("First Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column {
            Text(text = "Enter a Last Name")
            OutlinedTextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.lastName = it },
                label = { Text("Last Name") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column {
            Text(text = "Enter a Phone Number")
            OutlinedTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {viewModel.updateContact(contact)}, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = "Update Contact")
        }
    }
}