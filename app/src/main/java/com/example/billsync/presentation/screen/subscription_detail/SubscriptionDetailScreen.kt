package com.example.billsync.presentation.screen.subscription_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.extensions.toDisplayName
import com.example.billsync.presentation.state.SubscriptionDetailNavigationEvent
import com.example.billsync.presentation.viewmodel.SubscriptionDetailViewModel

@Composable
fun SubscriptionDetailScreen(
    subscriptionId: String,
    onNavigateBack: () -> Unit,
    onNavigateToEdit: (String) -> Unit,
    viewModel: SubscriptionDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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

            uiState.subscription != null -> {
                val subscription = uiState.subscription!!

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = subscription.brandName,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(text = subscription.displayAmount)
                    Text(text = subscription.dueDate.toString())

                    uiState.error?.let {
                        Text(text = it, color = MaterialTheme.colorScheme.error)
                    }

                    when (subscription.status) {
                        BillStatus.TRIAL -> {
                            Button(
                                onClick = viewModel::onConvertToPaid,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.convert_to_paid))
                            }
                        }

                        BillStatus.PAID -> {}

                        else -> {
                            Button(
                                onClick = viewModel::onMarkAsPaid,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.mark_as_paid))
                            }

                            if (subscription.paymentFrequency != PaymentFrequency.ONE_TIME) {
                                OutlinedButton(
                                    onClick = viewModel::onSkipCycle,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(stringResource(R.string.skip_cycle))
                                }
                            }
                        }
                    }

                    Button(
                        onClick = { onNavigateToEdit(subscriptionId) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.edit))
                    }

                    Button(
                        onClick = viewModel::onDelete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.delete))
                    }

                    // Payment History
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = stringResource(R.string.payment_history),
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (uiState.paymentHistory.isEmpty()) {
                        Text(
                            text = stringResource(R.string.payment_history_empty),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        uiState.paymentHistory.forEach { record ->
                            val typeLabel = record.type.toDisplayName()
                            Text(
                                text = buildString {
                                    append(record.formattedDate)
                                    append(" . ")
                                    append(typeLabel)
                                    record.displayAmount?.let { append(" . $it") }
                                },
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

            else -> Text(text = uiState.error ?: "Something went wrong") // TODO: hardcoded string
        }
    }
}
