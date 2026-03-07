package com.example.billsync.domain.repository

import com.example.billsync.domain.model.PaymentRecord
import kotlinx.coroutines.flow.Flow

interface PaymentHistoryRepository {
    fun getPaymentHistory(subscriptionId: String): Flow<List<PaymentRecord>>
    suspend fun recordPayment(paymentRecord: PaymentRecord)
}
