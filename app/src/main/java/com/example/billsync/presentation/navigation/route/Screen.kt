package com.example.billsync.presentation.navigation.route

import kotlinx.serialization.Serializable

@Serializable
data object Subscription

@Serializable
data class SubscriptionDetail(val subscriptionId: String)

@Serializable
data object CreateSubscription

@Serializable
data class EditSubscription(val subscriptionId: String)
