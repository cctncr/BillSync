package com.example.billsync.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.billsync.data.local.entity.PaymentRecordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentRecordDao {
    @Query("SELECT * FROM payment_records WHERE subscriptionId = :subscriptionId ORDER BY recordedAtEpochDay DESC")
    fun getPaymentHistory(subscriptionId: String): Flow<List<PaymentRecordEntity>>

    @Insert
    suspend fun insertPaymentRecord(entity: PaymentRecordEntity)
}
