package com.example.billsync.domain.extensions

import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun Subscription.daysUntilDue(currentDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(currentDate, dueDate)
}

fun Subscription.isOverdue(currentDate: LocalDate): Boolean {
    return daysUntilDue(currentDate) < 0
}

fun Subscription.monthlyNormalizedAmount(): BigDecimal = when (paymentFrequency) {
    PaymentFrequency.WEEKLY -> {
        amount.amount.multiply(BigDecimal("52"))
            .divide(BigDecimal("12"), 2, RoundingMode.HALF_UP)
    }

    PaymentFrequency.MONTHLY -> {
        amount.amount
    }

    PaymentFrequency.QUARTERLY -> {
        amount.amount.divide(BigDecimal("3"), 2, RoundingMode.HALF_UP)
    }

    PaymentFrequency.YEARLY -> {
        amount.amount.divide(BigDecimal("12"), 2, RoundingMode.HALF_UP)
    }

    PaymentFrequency.ONE_TIME -> {
        BigDecimal.ZERO
    }
}