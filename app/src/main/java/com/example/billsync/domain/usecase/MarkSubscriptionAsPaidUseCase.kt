package com.example.billsync.domain.usecase

import com.example.billsync.domain.extensions.markAsPaid
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import javax.inject.Inject

class MarkSubscriptionAsPaidUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        if (subscription.status == BillStatus.PAID) return
        repository.updateSubscription(subscription.markAsPaid())
    }
}
