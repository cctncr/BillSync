package com.example.billsync.presentation.state

import java.util.Currency

data class SettingsUiState(
    val userName: String = "",
    val selectedCurrency: Currency? = null,
    val availableCurrencies: List<Currency> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val navigationEvent: SettingsNavigationEvent? = null
)

sealed class SettingsNavigationEvent {
    data object NavigateBack : SettingsNavigationEvent()
}
