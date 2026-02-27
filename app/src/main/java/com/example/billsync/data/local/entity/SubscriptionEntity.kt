package com.example.billsync.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subscriptions")
data class SubscriptionEntity(
    @PrimaryKey
    val id: String,
    val brandName: String,
    val amountValue: String,
    val currencyCode: String,
    val dueDateEpochDay: Long,
    val status: String,
    val paymentFrequency: String,
    val brandColorHex: String,
    val brandIconId: String?
)