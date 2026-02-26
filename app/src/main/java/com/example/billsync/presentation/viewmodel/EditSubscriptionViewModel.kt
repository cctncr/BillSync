package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Money
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import com.example.billsync.domain.usecase.UpdateSubscriptionUseCase
import com.example.billsync.presentation.model.BRAND_COLORS
import com.example.billsync.presentation.navigation.route.EditSubscription
import com.example.billsync.presentation.state.EditSubscriptionNavigationEvent
import com.example.billsync.presentation.state.EditSubscriptionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency
import javax.inject.Inject

@HiltViewModel
class EditSubscriptionViewModel @Inject constructor(
    private val repository: SubscriptionRepository,
    private val updateSubscriptionUseCase: UpdateSubscriptionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val subscriptionId = savedStateHandle.toRoute<EditSubscription>().subscriptionId

    private val _uiState = MutableStateFlow(EditSubscriptionUiState())
    val uiState: StateFlow<EditSubscriptionUiState> = _uiState.asStateFlow()

    init {
        loadAvailableCurrencies()
        loadAvailableBrandColors()
        loadSubscription()
    }

    private fun loadSubscription() {
        viewModelScope.launch {
            val subscription = repository.getSubscriptionById(subscriptionId)
            if (subscription == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Subscription not found"
                    )
                } // TODO: Hardcoded String
            } else {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        brandName = subscription.brandName,
                        amount = subscription.amount.amount.toPlainString(),
                        selectedCurrency = subscription.amount.currency,
                        dueDate = subscription.dueDate,
                        selectedStatus = subscription.status,
                        selectedFrequency = subscription.paymentFrequency,
                        brandColorHex = subscription.brandColorHex,
                        brandIconId = subscription.brandIconId
                    )
                }
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
            _uiState.update { it.copy(error = "Please enter a valid amount") } // TODO: Hardcoded string
            return
        }

        val subscription = Subscription(
            id = subscriptionId,
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
                updateSubscriptionUseCase(subscription)
                _uiState.update { it.copy(navigationEvent = EditSubscriptionNavigationEvent.NavigateBack) }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to update"
                    )
                } // TODO: Hardcoded string
            }
        }
    }

    fun onNavigationEventConsumed() {
        _uiState.update { it.copy(navigationEvent = null) }
    }
}
