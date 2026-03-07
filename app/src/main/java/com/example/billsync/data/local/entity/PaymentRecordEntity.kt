package com.example.billsync.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "payment_records")
data class PaymentRecordEntity(
    @PrimaryKey
    val id: String,
    val subscriptionId: String,
    val recordedAtEpochDay: Long,
    val type: String,
    val amountValue: String?,
    val currencyCode: String?
)
