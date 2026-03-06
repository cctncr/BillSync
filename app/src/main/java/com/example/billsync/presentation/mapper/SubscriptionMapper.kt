package com.example.billsync.presentation.mapper

import com.example.billsync.domain.extensions.daysUntilDue
import com.example.billsync.domain.extensions.effectiveStatus
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.extensions.formatForDisplay
import com.example.billsync.presentation.extensions.toComposeColor
import java.time.LocalDate
import com.example.billsync.domain.model.Subscription as DomainSubscription
import com.example.billsync.presentation.model.Subscription as UiSubscription

fun DomainSubscription.toUi(): UiSubscription {
    val today = LocalDate.now()
    val effectiveStatus = effectiveStatus(today)
    val daysUntilDue = when {
        effectiveStatus == BillStatus.PAID && paymentFrequency == PaymentFrequency.ONE_TIME -> null
        else -> daysUntilDue(today)
    }

    return UiSubscription(
        id = id,
        brandName = brandName,
        displayAmount = amount.formatForDisplay(),
        dueDate = dueDate,
        daysUntilDue = daysUntilDue,
        status = effectiveStatus,
        brandColor = brandColorHex.toComposeColor(),
        paymentFrequency = paymentFrequency,
        brandIcon = null // TODO(add mapping),
    )
}