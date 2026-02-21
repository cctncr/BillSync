package com.example.billsync.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
data object Subscription

@Serializable
data class SubscriptionDetail(val subscriptionID: String)