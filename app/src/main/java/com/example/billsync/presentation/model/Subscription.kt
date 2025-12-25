package com.example.billsync.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import java.time.LocalDate

data class Subscription(
    val id: String,
    val brandName: String,
    val displayAmount: String,
    val dueDate: LocalDate,
    val status: BillStatus,
    val paymentFrequency: PaymentFrequency,
    val brandColor: Color,
    val brandIcon: ImageVector?
)