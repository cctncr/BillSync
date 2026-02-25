package com.example.billsync.presentation.state

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.presentation.model.Subscription

data class SubscriptionsUiState(
    val userName: String? = null,
    val userIcon: ImageVector? = null,
    val totalBalance: String = "",
    val avgDailyCost: String = "",
    val subscriptions: List<Subscription> = emptyList(),
    val activeSubCount: Int = 0,
    val statusFilterOptions: List<FilterOption<BillStatus>> = emptyList(),
    val frequencyFilterOptions: List<FilterOption<PaymentFrequency>> = emptyList(),
    val currentSortOption: BillSortOption = BillSortOption.REVENUE_DATE_ASC,
    val isLoading: Boolean = true,
    val error: String? = null,
)
