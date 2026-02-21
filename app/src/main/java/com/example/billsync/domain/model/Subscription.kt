package com.example.billsync.domain.model

import java.time.LocalDate

data class Subscription(
    val id: String,
    val brandName: String,
    val amount: Money,
    val dueDate: LocalDate,
    val status: BillStatus,
    val paymentFrequency: PaymentFrequency,
    val brandColorHex: String,
    val brandIconId: String?
)