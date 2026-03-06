package com.example.billsync.domain.extensions

import com.example.billsync.domain.model.PaymentFrequency
import java.time.LocalDate

fun LocalDate.nextBillingDate(frequency: PaymentFrequency): LocalDate = when (frequency) {
    PaymentFrequency.WEEKLY -> plusWeeks(1)
    PaymentFrequency.MONTHLY -> plusMonths(1)
    PaymentFrequency.QUARTERLY -> plusMonths(3)
    PaymentFrequency.YEARLY -> plusYears(1)
    PaymentFrequency.ONE_TIME -> this
}
