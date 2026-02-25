package com.example.billsync.presentation.screen.subscription_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.billsync.presentation.state.SubscriptionDetailNavigationEvent
import com.example.billsync.presentation.viewmodel.SubscriptionDetailViewModel

@Composable
fun SubscriptionDetailScreen(
    subscriptionId: String,
    onNavigateBack: () -> Unit,
    viewModel: SubscriptionDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            is SubscriptionDetailNavigationEvent.NavigateBack -> {
                onNavigateBack()
                viewModel.onNavigationEventConsumed()
            }

            else -> Unit
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when {
            uiState.isLoading -> CircularProgressIndicator()

            uiState.subscription != null -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = uiState.subscription!!.brandName,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(text = uiState.subscription!!.displayAmount)
                Text(text = uiState.subscription!!.dueDate.toString())

                uiState.error?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    onClick = viewModel::onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete") // TODO: Hardcoded str
                }
            }

            else -> Text(text = uiState.error ?: "Something went wrong") // TODO: hardcoded string
        }
    }
}