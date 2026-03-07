package com.example.billsync.domain.usecase

import com.example.billsync.domain.extensions.markAsPaid
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentRecord
import com.example.billsync.domain.model.PaymentType
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.PaymentHistoryRepository
import com.example.billsync.domain.repository.SubscriptionRepository
import java.time.LocalDate
import java.util.UUID
import javax.inject.Inject

class MarkSubscriptionAsPaidUseCase @Inject constructor(
    private val repository: SubscriptionRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        if (subscription.status == BillStatus.PAID) return
        if (subscription.status == BillStatus.TRIAL) return
        repository.updateSubscription(subscription.markAsPaid())
        paymentHistoryRepository.recordPayment(
            PaymentRecord(
                id = UUID.randomUUID().toString(),
                subscriptionId = subscription.id,
                recordedAt = LocalDate.now(),
                type = PaymentType.PAID,
                amount = subscription.amount
            )
        )
    }
}
