package com.example.billsync.presentation.screen.settings

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.billsync.R
import com.example.billsync.presentation.common_components.CurrencyPickerBottomSheet
import com.example.billsync.presentation.preview.SettingsPreviewProvider
import com.example.billsync.presentation.screen.settings.components.DefaultCurrencyRow
import com.example.billsync.presentation.screen.settings.components.SettingsSectionHeader
import com.example.billsync.presentation.state.SettingsNavigationEvent
import com.example.billsync.presentation.state.SettingsUiState
import com.example.billsync.presentation.viewmodel.SettingsViewModel
import java.util.Currency

@Composable
fun SettingsScreen(
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.navigationEvent) {
        when (uiState.navigationEvent) {
            SettingsNavigationEvent.NavigateBack -> {
                onNavigateBack()
                viewModel.onNavigationEventConsumed()
            }

            null -> Unit
        }
    }

    SettingsContent(
        uiState = uiState,
        onNavigateBack = onNavigateBack,
        onUserNameChange = viewModel::onUserNameChange,
        onCurrencySelected = viewModel::onCurrencySelected,
        onClearCurrency = viewModel::onClearCurrency,
        onSave = viewModel::onSave
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsContent(
    uiState: SettingsUiState,
    onNavigateBack: () -> Unit,
    onUserNameChange: (String) -> Unit,
    onCurrencySelected: (Currency) -> Unit,
    onClearCurrency: () -> Unit,
    onSave: () -> Unit
) {
    var showCurrencyPicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->

        if (uiState.isLoading) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SettingsSectionHeader(title = stringResource(R.string.settings_section_profile))

            OutlinedTextField(
                value = uiState.userName,
                onValueChange = onUserNameChange,
                label = { Text(stringResource(R.string.settings_name_label)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            SettingsSectionHeader(title = stringResource(R.string.settings_section_preferences))

            DefaultCurrencyRow(
                label = stringResource(R.string.settings_default_currency_label),
                currency = uiState.selectedCurrency,
                notSetText = stringResource(R.string.settings_default_currency_not_set),
                onClick = { showCurrencyPicker = true }
            )

            if (uiState.selectedCurrency != null) {
                TextButton(
                    onClick = onClearCurrency,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(stringResource(R.string.settings_reset_to_auto))
                }
            }

            uiState.error?.let { errorMessage ->
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Button(
                onClick = onSave,
                modifier = Modifier.fillMaxWidth()
            ) {
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

@Preview(showBackground = true)
@Composable
private fun SettingsContent_Preview(
    @PreviewParameter(SettingsPreviewProvider::class) uiState: SettingsUiState
) {
    SettingsContent(
        uiState = uiState,
        onNavigateBack = { },
        onUserNameChange = { },
        onCurrencySelected = { },
        onClearCurrency = { },
        onSave = { }
    )
}