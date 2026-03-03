package com.example.billsync.presentation.state

data class SettingsUiState(
    val userName: String = "",
    val isLoading: Boolean = true,
    val error: String? = null,
    val navigationEvent: SettingsNavigationEvent? = null
)

sealed class SettingsNavigationEvent {
    data object NavigateBack : SettingsNavigationEvent()
}
