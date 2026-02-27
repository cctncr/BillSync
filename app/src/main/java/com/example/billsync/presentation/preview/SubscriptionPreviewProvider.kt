package com.example.billsync.presentation.preview

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.presentation.model.Subscription
import com.example.billsync.presentation.state.SubscriptionsUiState
import java.time.LocalDate

class SubscriptionPreviewProvider : PreviewParameterProvider<SubscriptionsUiState> {
    override val values: Sequence<SubscriptionsUiState> = sequenceOf(
        withData,
        loading,
        empty
    )

    companion object {
        val withData = SubscriptionsUiState(
            userName = "Alex Adams",
            totalBalance = "$80.49",
            avgDailyCost = "$4.90/day",
            activeSubCount = 4,
            isLoading = false,
            currentSortOption = BillSortOption.REVENUE_DATE_ASC,
            statusFilterOptions = listOf(
                FilterOption.All(isSelected = true),
                FilterOption.Specific(value = BillStatus.PENDING, isSelected = false),
                FilterOption.Specific(value = BillStatus.OVERDUE, isSelected = false),
                FilterOption.Specific(value = BillStatus.PAID, isSelected = false),
            ),
            frequencyFilterOptions = listOf(
                FilterOption.All(isSelected = true),
                FilterOption.Specific(value = PaymentFrequency.MONTHLY, isSelected = false),
                FilterOption.Specific(value = PaymentFrequency.YEARLY, isSelected = false),
            ),
            subscriptions = listOf(
                Subscription(
                    id = "1",
                    brandName = "Netflix",
                    displayAmount = "$19.99/mo",
                    dueDate = LocalDate.now().plusDays(5),
                    status = BillStatus.PENDING,
                    paymentFrequency = PaymentFrequency.MONTHLY,
                    brandColor = Color(0xFFE50914),
                    brandIcon = null
                ),
                Subscription(
                    id = "2",
                    brandName = "Spotify",
                    displayAmount = "$9.99/mo",
                    dueDate = LocalDate.now().plusDays(12),
                    status = BillStatus.PENDING,
                    paymentFrequency = PaymentFrequency.MONTHLY,
                    brandColor = Color(0xFF1DB954),
                    brandIcon = null
                ),
                Subscription(
                    id = "3",
                    brandName = "Amazon Prime",
                    displayAmount = "$14.99/mo",
                    dueDate = LocalDate.now().minusDays(2),
                    status = BillStatus.OVERDUE,
                    paymentFrequency = PaymentFrequency.MONTHLY,
                    brandColor = Color(0xFFFF9900),
                    brandIcon = null
                ),
                Subscription(
                    id = "4",
                    brandName = "GitHub Pro",
                    displayAmount = "$7.00/mo",
                    dueDate = LocalDate.now().plusDays(20),
                    status = BillStatus.PENDING,
                    paymentFrequency = PaymentFrequency.MONTHLY,
                    brandColor = Color(0xFF181717),
                    brandIcon = null
                ),
            )
        )

        val loading = SubscriptionsUiState(
            isLoading = true
        )

        val empty = SubscriptionsUiState(
            userName = "Alex Adams",
            totalBalance = "$0.00",
            avgDailyCost = "$0.00/day",
            activeSubCount = 0,
            isLoading = false,
            subscriptions = emptyList()
        )
    }
}