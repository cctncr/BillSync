package com.example.billsync.domain.model

import java.time.LocalDate

data class PaymentRecord(
    val id: String,
    val subscriptionId: String,
    val recordedAt: LocalDate,
    val type: PaymentType,
    val amount: Money?
)
