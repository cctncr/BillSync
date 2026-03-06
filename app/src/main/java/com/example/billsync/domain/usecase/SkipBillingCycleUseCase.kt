package com.example.billsync.domain.usecase

import com.example.billsync.domain.extensions.skipCycle
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import javax.inject.Inject

class SkipBillingCycleUseCase @Inject constructor(
    private val repository: SubscriptionRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        if (subscription.paymentFrequency == PaymentFrequency.ONE_TIME) return
        if (subscription.status == BillStatus.PAID) return

        repository.updateSubscription(subscription.skipCycle())
    }
}
