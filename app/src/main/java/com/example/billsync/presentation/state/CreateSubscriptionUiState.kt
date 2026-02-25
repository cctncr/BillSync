package com.example.billsync.presentation.state

import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import java.time.LocalDate
import java.util.Currency

data class CreateSubscriptionUiState(
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
    val isLoading: Boolean = false,
    val isSaved: Boolean = false,
    val error: String? = null
)