package com.example.billsync.data.repository

import com.example.billsync.data.local.dao.SubscriptionDao
import com.example.billsync.data.mapper.toDomain
import com.example.billsync.data.mapper.toEntity
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val dao: SubscriptionDao
) : SubscriptionRepository {

    override fun getAllSubscriptions(): Flow<List<Subscription>> {
        return dao.getAllSubscriptions().map { entities -> entities.map { it.toDomain() } }
    }

    override fun observeSubscriptionById(id: String): Flow<Subscription?> {
        return dao.observeSubscriptionById(id).map { it?.toDomain() }
    }

    override suspend fun getSubscriptionById(id: String): Subscription? {
        return dao.getSubscriptionById(id)?.toDomain()
    }

    override suspend fun insertSubscription(subscription: Subscription) {
        dao.upsertSubscription(subscription.toEntity())
    }

    override suspend fun updateSubscription(subscription: Subscription) {
        dao.updateSubscription(subscription.toEntity())
    }

    override suspend fun deleteSubscription(id: String) {
        dao.deleteSubscriptionById(id)
    }
}