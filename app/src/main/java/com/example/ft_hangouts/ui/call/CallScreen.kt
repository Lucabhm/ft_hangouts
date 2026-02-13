package com.example.ft_hangouts.ui.call

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ft_hangouts.data.repository.UIResult
import com.example.ft_hangouts.data.model.Call
import com.example.ft_hangouts.ui.components.CallCard

@Composable
fun CallScreen(modifier: Modifier = Modifier, viewModel: CallViewModel) {
    LaunchedEffect(Unit) {
        viewModel.loadCalls()
    }

    val state by viewModel.state.collectAsState()

    when (state) {
        is UIResult.Loading -> {}
        is UIResult.Success -> {
            val calls = (state as UIResult.Success<List<Call>>).data

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

        is UIResult.NotFound -> {}
        else -> {}
    }
}
