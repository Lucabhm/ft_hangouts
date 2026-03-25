package com.example.ft_hangouts.ui.addContact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.R
import com.example.ft_hangouts.data.model.ContactUIState
import com.example.ft_hangouts.data.model.firstNameError
import com.example.ft_hangouts.data.model.lastNameError
import com.example.ft_hangouts.data.model.phoneNumberError
import com.example.ft_hangouts.ui.components.PickProfileImage

@Composable
fun AddContactScreen(
    modifier: Modifier = Modifier,
    viewModel: AddContactViewModel,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val uiState = viewModel.state.collectAsState(ContactUIState.Loading)

    LaunchedEffect(Unit) {
        viewModel.state.collect {
            if (it is ContactUIState.Success) {
                viewModel.phoneNumber = null
                viewModel.profilePic = null
                viewModel.lastName = null
                viewModel.firstName = null
                onBack()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        PickProfileImage(viewModel.profilePic ?: "") { path -> viewModel.profilePic = path }

        Column {
            Text(text = stringResource(R.string.add_contact_first_name))
            OutlinedTextField(
                value = viewModel.firstName ?: "",
                onValueChange = { viewModel.firstName = it },
                label = { Text(stringResource(R.string.add_contact_fist_name_input)) },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    uiState.value.firstNameError()?.let { Text(it) }
                },
                isError = uiState.value.firstNameError() != null
            )
        }

        Column {
            Text(text = stringResource(R.string.add_contact_last_name))
            OutlinedTextField(
                value = viewModel.lastName ?: "",
                onValueChange = { viewModel.lastName = it },
                label = { Text(stringResource(R.string.add_contact_last_name_input)) },
                modifier = Modifier.fillMaxWidth(),
                supportingText = {
                    uiState.value.lastNameError()?.let { Text(it) }
                },
                isError = uiState.value.lastNameError() != null
            )
        }

        Column {
            Text(text = stringResource(R.string.add_contact_phone_number))
            OutlinedTextField(
                value = viewModel.phoneNumber ?: "",
                onValueChange = { viewModel.phoneNumber = it },
                label = { Text(stringResource(R.string.add_contact_phone_number_input)) },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                supportingText = {
                    uiState.value.phoneNumberError()?.let { Text(it) }
                },
                isError = uiState.value.phoneNumberError() != null
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.saveContact() }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(text = stringResource(R.string.add_contact_create_button))
        }
    }
}
