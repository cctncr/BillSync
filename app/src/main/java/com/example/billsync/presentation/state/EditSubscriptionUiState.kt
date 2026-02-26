package com.example.billsync.presentation.state

import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import java.time.LocalDate
import java.util.Currency

data class EditSubscriptionUiState(
    val brandName: String = "",
    val amount: String = "",
    val selectedCurrency: Currency = Currency.getInstance("USD"),
    val availableCurrencies: List<Currency> = emptyList(),
    val availableBrandColors: List<String> = emptyList(),
    val dueDate: LocalDate = LocalDate.now(),
    val selectedStatus: BillStatus = BillStatus.PENDING,
    val selectedFrequency: PaymentFrequency = PaymentFrequency.MONTHLY,
    val brandColorHex: String = "#9E9E9E",
    val brandIconId: String? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val navigationEvent: EditSubscriptionNavigationEvent? = null
)

sealed class EditSubscriptionNavigationEvent {
    data object NavigateBack : EditSubscriptionNavigationEvent()
}
