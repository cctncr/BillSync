package com.example.billsync.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.billsync.data.local.dao.SubscriptionDao
import com.example.billsync.data.local.entity.SubscriptionEntity

@Database(
    entities = [SubscriptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BillSyncDatabase : RoomDatabase() {
    abstract fun subscriptionDao(): SubscriptionDao
}