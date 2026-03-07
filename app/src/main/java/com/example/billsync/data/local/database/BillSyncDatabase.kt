package com.example.billsync.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.billsync.data.local.dao.PaymentRecordDao
import com.example.billsync.data.local.dao.SubscriptionDao
import com.example.billsync.data.local.entity.PaymentRecordEntity
import com.example.billsync.data.local.entity.SubscriptionEntity

@Database(
    entities = [SubscriptionEntity::class, PaymentRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BillSyncDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun paymentRecordDao(): PaymentRecordDao
}
