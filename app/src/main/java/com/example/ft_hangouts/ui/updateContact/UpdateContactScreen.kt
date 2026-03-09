package com.example.ft_hangouts.ui.updateContact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.Contact
import com.example.ft_hangouts.data.repository.UIResult
import com.example.ft_hangouts.ui.components.PickProfileImage

@Composable
fun UpdateContactScreen(
    modifier: Modifier = Modifier,
    viewModel: UpdateContactViewModel,
    contact: Contact,
    onBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.firstName = contact.firstName ?: ""
        viewModel.lastName = contact.lastName ?: ""
        viewModel.phoneNumber = contact.phoneNumber
        viewModel.profilePic = contact.profilePicture ?: ""
    }

    LaunchedEffect(Unit) {
        viewModel.state.collect {
            if (it is UIResult.Success) {
                viewModel.phoneNumber = ""
                viewModel.profilePic = ""
                viewModel.lastName = ""
                viewModel.firstName = ""

                viewModel.resetState()
                onBack()
            }
        }
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PickProfileImage(viewModel.profilePic) { path -> viewModel.profilePic = path }

        Column {
            Text(text = stringResource(R.string.add_contact_first_name))

            OutlinedTextField(
                value = viewModel.firstName,
                onValueChange = { viewModel.firstName = it },
                label = { Text(stringResource(R.string.add_contact_fist_name_input)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column {
            Text(text = stringResource(R.string.add_contact_last_name))
            OutlinedTextField(
                value = viewModel.lastName,
                onValueChange = { viewModel.lastName = it },
                label = { Text(stringResource(R.string.add_contact_last_name_input)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Column {
            Text(text = stringResource(R.string.add_contact_phone_number))
            OutlinedTextField(
                value = viewModel.phoneNumber,
                onValueChange = { viewModel.phoneNumber = it },
                label = { Text(stringResource(R.string.add_contact_phone_number_input)) },
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.updateContact(contact) }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.add_contact_create_button))
        }
    }
}