package com.example.billsync.presentation.state

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.presentation.model.Subscription

data class SubscriptionsUiState(
    val userName: String = "User",
    val userIcon: ImageVector? = null,
    val greetingText: String = "Welcome back,",
    val totalBalance: String = String(),
    val avgDailyCost: String = String(),
    val balanceLabel: String = "Monthly",
    val subscriptions: List<Subscription> = emptyList(),
    val activeSubCount: Int = -1,
    val statusFilterOptions: List<FilterOption<BillStatus>> = listOf(
        FilterOption.All(isSelected = true)
    ),
    val frequencyFilterOptions: List<FilterOption<PaymentFrequency>> = listOf(
        FilterOption.All(isSelected = true)
    ),
    val currentSortOption: BillSortOption = BillSortOption.REVENUE_DATE_ASC,
    val isLoading: Boolean = true,
    val error: String? = null,
)
