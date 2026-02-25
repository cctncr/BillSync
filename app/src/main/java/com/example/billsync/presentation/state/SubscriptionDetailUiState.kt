package com.example.billsync.presentation.state

import com.example.billsync.presentation.model.Subscription

data class SubscriptionDetailUiState(
    val subscription: Subscription? = null,
    val isLoading: Boolean = true,
    val error: String? = null,
    val navigationEvent: SubscriptionDetailNavigationEvent? = null
)

sealed class SubscriptionDetailNavigationEvent {
    data object NavigateBack : SubscriptionDetailNavigationEvent()
}