package com.example.billsync.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.model.BRAND_COLORS
import com.example.billsync.presentation.state.CreateSubscriptionUiState
import java.time.LocalDate
import java.util.Currency

class CreateSubscriptionPreviewProvider : PreviewParameterProvider<CreateSubscriptionUiState> {
    override val values: Sequence<CreateSubscriptionUiState> = sequenceOf(
        empty,
        filledIn,
        withError,
        loading
    )

    companion object {
        val empty = CreateSubscriptionUiState(
            availableBrandColors = BRAND_COLORS
        )

        val filledIn = CreateSubscriptionUiState(
            brandName = "Netflix",
            amount = "19.99",
            selectedCurrency = Currency.getInstance("USD"),
            dueDate = LocalDate.of(2026, 3, 15),
            selectedStatus = BillStatus.PENDING,
            selectedFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#E53935",
            availableBrandColors = BRAND_COLORS
        )

        val withError = CreateSubscriptionUiState(
            availableBrandColors = BRAND_COLORS,
            error = "Brand name is required"
        )

        val loading = CreateSubscriptionUiState(
            brandName = "Netflix",
            amount = "19.99",
            availableBrandColors = BRAND_COLORS,
            isLoading = true
        )
    }
}