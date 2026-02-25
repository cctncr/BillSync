package com.example.billsync.domain.usecase

import com.example.billsync.domain.repository.SubscriptionRepository
import javax.inject.Inject

class DeleteSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteSubscription(id)
    }
}