package com.example.billsync.di.module

import android.content.Context
import androidx.room.Room
import com.example.billsync.data.local.dao.SubscriptionDao
import com.example.billsync.data.local.database.BillSyncDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): BillSyncDatabase {
        return Room.databaseBuilder(
            context,
            BillSyncDatabase::class.java,
            "billsync_database"
        ).build()
    }

    @Provides
    fun provideSubscriptionDao(database: BillSyncDatabase): SubscriptionDao {
        return database.subscriptionDao()
    }
}