package com.example.billsync.domain.usecase

import com.example.billsync.domain.extensions.skipCycle
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.PaymentRecord
import com.example.billsync.domain.model.PaymentType
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.PaymentHistoryRepository
import com.example.billsync.domain.repository.SubscriptionRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class SkipBillingCycleUseCase @Inject constructor(
    private val repository: SubscriptionRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        if (subscription.paymentFrequency == PaymentFrequency.ONE_TIME) return
        if (subscription.status == BillStatus.PAID) return
        if (subscription.status == BillStatus.TRIAL) return

        repository.updateSubscription(subscription.skipCycle())
        paymentHistoryRepository.recordPayment(
            PaymentRecord(
                id = UUID.randomUUID().toString(),
                subscriptionId = subscription.id,
                recordedAt = LocalDate.now(),
                type = PaymentType.SKIPPED,
                amount = null
            )
        )
    }
}
