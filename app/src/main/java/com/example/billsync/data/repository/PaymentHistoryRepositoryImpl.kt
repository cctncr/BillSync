package com.example.billsync.data.repository

import com.example.billsync.data.local.dao.PaymentRecordDao
import com.example.billsync.data.mapper.toDomain
import com.example.billsync.data.mapper.toEntity
import com.example.billsync.domain.model.PaymentRecord
import com.example.billsync.domain.repository.PaymentHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PaymentHistoryRepositoryImpl @Inject constructor(
    private val dao: PaymentRecordDao
) : PaymentHistoryRepository {

    override fun getPaymentHistory(subscriptionId: String): Flow<List<PaymentRecord>> {
        return dao.getPaymentHistory(subscriptionId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun recordPayment(paymentRecord: PaymentRecord) {
        dao.insertPaymentRecord(paymentRecord.toEntity())
    }
}
