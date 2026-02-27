package com.example.billsync.domain.repository

import com.example.billsync.domain.model.Subscription
import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    fun getAllSubscriptions(): Flow<List<Subscription>>
    fun observeSubscriptionById(id: String): Flow<Subscription?>
    suspend fun getSubscriptionById(id: String): Subscription?
    suspend fun insertSubscription(subscription: Subscription)
    suspend fun updateSubscription(subscription: Subscription)
    suspend fun deleteSubscription(id: String)
}