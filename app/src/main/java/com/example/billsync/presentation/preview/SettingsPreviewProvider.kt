package com.example.billsync.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.billsync.presentation.state.SettingsUiState
import java.util.Currency

class SettingsPreviewProvider : PreviewParameterProvider<SettingsUiState> {
    override val values: Sequence<SettingsUiState> = sequenceOf(
        withData,
        withNullCurrency,
        loading
    )

    companion object {
        val withData = SettingsUiState(
            userName = "Alex Adams",
            selectedCurrency = Currency.getInstance("USD"),
            availableCurrencies = listOf(
                Currency.getInstance("USD"),
                Currency.getInstance("EUR"),
                Currency.getInstance("TRY"),
            ),
            isLoading = false
        )

        val loading = SettingsUiState(
            isLoading = true
        )

        val withNullCurrency = SettingsUiState(
            userName = "Alex Adams",
            selectedCurrency = null,
            availableCurrencies = listOf(
                Currency.getInstance("USD"),
                Currency.getInstance("EUR"),
                Currency.getInstance("TRY"),
            ),
            isLoading = false
        )
    }
}
