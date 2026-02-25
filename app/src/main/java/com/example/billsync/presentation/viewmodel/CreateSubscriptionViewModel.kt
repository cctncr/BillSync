package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Money
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.UserPreferencesRepository
import com.example.billsync.domain.usecase.SaveSubscriptionUseCase
import com.example.billsync.presentation.model.BRAND_COLORS
import com.example.billsync.presentation.state.CreateSubscriptionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateSubscriptionViewModel @Inject constructor(
    private val saveSubscriptionUseCase: SaveSubscriptionUseCase,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateSubscriptionUiState())
    val uiState: StateFlow<CreateSubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadAvailableCurrencies()
        loadAvailableBrandColors()
        observeDefaultCurrency()
    }

    private fun observeDefaultCurrency() {
        viewModelScope.launch {
            userPreferencesRepository.defaultCurrencyCode.collect { code ->
                val currency = code?.let { Currency.getInstance(it) }
                    ?: runCatching { Currency.getInstance(Locale.getDefault()) }
                        .getOrElse { Currency.getInstance("USD") }

                _uiState.update { it.copy(selectedCurrency = currency) }
            }
        }
    }

    private fun loadAvailableCurrencies() {
        val currencies = Currency.getAvailableCurrencies()
            .sortedBy { it.currencyCode }
        _uiState.update { it.copy(availableCurrencies = currencies) }
    }

    private fun loadAvailableBrandColors() {
        _uiState.update { it.copy(availableBrandColors = BRAND_COLORS) }
    }

    fun onBrandNameChange(brandName: String) {
        _uiState.update { it.copy(brandName = brandName) }
    }

    fun onAmountChange(amount: String) {
        _uiState.update { it.copy(amount = amount) }
    }

    fun onCurrencySelected(currency: Currency) {
        _uiState.update { it.copy(selectedCurrency = currency) }
    }

    fun onDueDateChange(dueDate: LocalDate) {
        _uiState.update { it.copy(dueDate = dueDate) }
    }

    fun onStatusSelected(status: BillStatus) {
        _uiState.update { it.copy(selectedStatus = status) }
    }

    fun onFrequencySelected(frequency: PaymentFrequency) {
        _uiState.update { it.copy(selectedFrequency = frequency) }
    }

    fun onBrandColorChange(colorHex: String) {
        _uiState.update { it.copy(brandColorHex = colorHex) }
    }

    fun onSave() {
        val state = _uiState.value

        if (state.brandName.isBlank()) {
            _uiState.update { it.copy(error = "Brand name is required") } // TODO: Hardcoded string
            return
        }

        val parsedAmount = state.amount.toBigDecimalOrNull()

        if (parsedAmount == null || parsedAmount <= BigDecimal.ZERO) {
            _uiState.update { it.copy(error = "Please enter a valid amount") }
            return
        }

        val subscription = Subscription(
            id = UUID.randomUUID().toString(),
            brandName = state.brandName,
            amount = Money(amount = parsedAmount, currency = state.selectedCurrency),
            dueDate = state.dueDate,
            status = state.selectedStatus,
            paymentFrequency = state.selectedFrequency,
            brandColorHex = state.brandColorHex,
            brandIconId = state.brandIconId
        )

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                saveSubscriptionUseCase(subscription)
                _uiState.update { it.copy(isLoading = false, isSaved = true) }
            } catch (_: Exception) {
                _uiState.update { it.copy(isLoading = false, error = "Failed to save") } // TODO: Hardcoded String
            }
        }
    }
}