package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsync.domain.repository.UserPreferencesRepository
import com.example.billsync.presentation.state.SettingsNavigationEvent
import com.example.billsync.presentation.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadSettings()
        loadAvailableCurrencies()
    }

    private fun loadSettings() {
        viewModelScope.launch {
            val name = userPreferencesRepository.userName.first() ?: ""
            val currencyCode = userPreferencesRepository.userDefaultCurrencyCode.first()
            val currency = currencyCode?.let {
                runCatching { Currency.getInstance(it) }.getOrNull()
            }

            _uiState.update {
                it.copy(
                    userName = name,
                    selectedCurrency = currency,
                    isLoading = false
                )
            }
        }
    }

    private fun loadAvailableCurrencies() {
        _uiState.update {
            it.copy(
                availableCurrencies = Currency.getAvailableCurrencies()
                    .sortedBy { currency -> currency.currencyCode }
            )
        }
    }

    fun onUserNameChange(name: String) {
        _uiState.update { it.copy(userName = name) }
    }

    fun onCurrencySelected(currency: Currency) {
        _uiState.update { it.copy(selectedCurrency = currency) }
    }

    fun onClearCurrency() {
        _uiState.update { it.copy(selectedCurrency = null) }
    }

    fun onSave() {
        val state = _uiState.value

        if (state.userName.isBlank()) {
            _uiState.update { it.copy(error = "Name cannot be empty") } // TODO: Hardcoded string
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                userPreferencesRepository.setUserName(state.userName)
                val currency = state.selectedCurrency
                if (currency != null) {
                    userPreferencesRepository.setUserDefaultCurrency(currency.currencyCode)
                } else {
                    userPreferencesRepository.clearUserDefaultCurrency()
                }
                _uiState.update { it.copy(navigationEvent = SettingsNavigationEvent.NavigateBack) }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to save"
                    )
                } // TODO: Hardcoded string
            }
        }
    }

    fun onNavigationEventConsumed() {
        _uiState.update { it.copy(navigationEvent = null) }
    }
}
