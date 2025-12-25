package com.example.billsync.presentation.mapper

import com.example.billsync.presentation.extensions.formatForDisplay
import com.example.billsync.presentation.extensions.toComposeColor
import com.example.billsync.domain.model.Subscription as DomainSubscription
import com.example.billsync.presentation.model.Subscription as UiSubscription

fun DomainSubscription.toUi(): UiSubscription {
    return UiSubscription(
        id = id,
        brandName = brandName,
        displayAmount = amount.formatForDisplay(),
        dueDate = dueDate,
        status = status,
        brandColor = brandColorHex.toComposeColor(),
        paymentFrequency = paymentFrequency,
        brandIcon = null // TODO(add mapping)
    )
}