package com.example.billsync.presentation.model

import com.example.billsync.domain.model.PaymentType

data class PaymentRecord(
    val id: String,
    val formattedDate: String,
    val type: PaymentType,
    val displayAmount: String?
)
