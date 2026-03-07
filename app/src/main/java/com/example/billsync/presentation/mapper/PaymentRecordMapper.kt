package com.example.billsync.presentation.mapper

import com.example.billsync.presentation.extensions.formatForDisplay
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.example.billsync.domain.model.PaymentRecord as DomainPaymentRecord
import com.example.billsync.presentation.model.PaymentRecord as UiPaymentRecord

fun DomainPaymentRecord.toUi(): UiPaymentRecord = UiPaymentRecord(
    id = id,
    formattedDate = recordedAt.format(
        DateTimeFormatter.ofLocalizedDate(
            FormatStyle.MEDIUM
        )
    ),
    type = type,
    displayAmount = amount?.formatForDisplay()
)
