package com.example.billsync.presentation.mapper

import android.content.Context
import com.example.billsync.presentation.extensions.formatForDisplay
import com.example.billsync.presentation.extensions.toAbbreviation
import com.example.billsync.presentation.extensions.toComposeColor
import com.example.billsync.presentation.extensions.toDisplayName
import com.example.billsync.presentation.extensions.toDueDateMessage
import java.time.LocalDate
import com.example.billsync.domain.model.Subscription as DomainSubscription
import com.example.billsync.presentation.model.Subscription as UiSubscription

fun DomainSubscription.toUi(context: Context, currentDate: LocalDate): UiSubscription {
    return UiSubscription(
        id = id,
        brandName = brandName,
        displayAmount = amount.formatForDisplay(),
        dueDateMessage = dueDate.toDueDateMessage(context, currentDate = currentDate),
        status = status.toDisplayName(context),
        brandColor = brandColorHex.toComposeColor(),
        paymentFrequency = paymentFrequency.toAbbreviation(context),
        brandIcon = null // TODO(add mapping)
    )
}