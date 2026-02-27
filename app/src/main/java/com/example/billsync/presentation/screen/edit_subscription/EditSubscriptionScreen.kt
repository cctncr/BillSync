package com.example.billsync.presentation.screen.edit_subscription

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.screen.create_subscription.components.AmountSelectionCard
import com.example.billsync.presentation.screen.create_subscription.components.BrandColorPicker
import com.example.billsync.presentation.screen.create_subscription.components.BrandNameField
import com.example.billsync.presentation.screen.create_subscription.components.CurrencyPickerBottomSheet
import com.example.billsync.presentation.screen.create_subscription.components.DueDateField
import com.example.billsync.presentation.screen.create_subscription.components.FrequencySelector
import com.example.billsync.presentation.screen.create_subscription.components.StatusSelector
import com.example.billsync.presentation.state.EditSubscriptionNavigationEvent
import com.example.billsync.presentation.state.EditSubscriptionUiState
import com.example.billsync.presentation.viewmodel.EditSubscriptionViewModel
import java.time.LocalDate
import java.util.Currency

@Composable
fun EditSubscriptionScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditSubscriptionViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            is EditSubscriptionNavigationEvent.NavigateBack -> {
                onNavigateBack()
                viewModel.onNavigationEventConsumed()
            }

            null -> Unit
        }
    }

    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    EditSubscriptionContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onBrandNameChange = viewModel::onBrandNameChange,
        onAmountChange = viewModel::onAmountChange,
        onCurrencySelected = viewModel::onCurrencySelected,
        onDueDateChange = viewModel::onDueDateChange,
        onFrequencySelected = viewModel::onFrequencySelected,
        onStatusSelected = viewModel::onStatusSelected,
        onBrandColorChange = viewModel::onBrandColorChange,
        onSave = viewModel::onSave
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditSubscriptionContent(
    uiState: EditSubscriptionUiState,
    onNavigateBack: () -> Unit,
    onBrandNameChange: (String) -> Unit,
    onAmountChange: (String) -> Unit,
    onCurrencySelected: (Currency) -> Unit,
    onDueDateChange: (LocalDate) -> Unit,
    onFrequencySelected: (PaymentFrequency) -> Unit,
    onStatusSelected: (BillStatus) -> Unit,
    onBrandColorChange: (String) -> Unit,
    onSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showCurrencyPicker by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.edit_subscription_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BrandNameField(value = uiState.brandName, onValueChange = onBrandNameChange)

            AmountSelectionCard(
                amount = uiState.amount,
                onAmountChange = onAmountChange,
                selectedCurrency = uiState.selectedCurrency,
                onCurrencyPickerClick = { showCurrencyPicker = true }
            )

            DueDateField(dueDate = uiState.dueDate, onDateSelected = onDueDateChange)

            BrandColorPicker(
                colors = uiState.availableBrandColors,
                selectedColorHex = uiState.brandColorHex,
                onColorSelected = onBrandColorChange
            )

            FrequencySelector(
                selectedFrequency = uiState.selectedFrequency,
                onFrequencySelected = onFrequencySelected
            )

            StatusSelector(
                selectedStatus = uiState.selectedStatus,
                onStatusSelected = onStatusSelected
            )

            uiState.error?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(onClick = onSave, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.save))
            }
        }
    }

    if (showCurrencyPicker) {
        CurrencyPickerBottomSheet(
            currencies = uiState.availableCurrencies,
            onCurrencySelected = onCurrencySelected,
            onDismiss = { showCurrencyPicker = false }
        )
    }
}
