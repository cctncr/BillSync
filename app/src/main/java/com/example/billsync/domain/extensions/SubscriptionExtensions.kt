package com.example.billsync.domain.extensions

import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Money
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

fun Subscription.effectiveStatus(currentDate: LocalDate): BillStatus = when {
    status == BillStatus.TRIAL -> BillStatus.TRIAL
    status == BillStatus.PAID && paymentFrequency == PaymentFrequency.ONE_TIME -> BillStatus.PAID
    status == BillStatus.PAID && !isOverdue(currentDate) -> BillStatus.PAID
    isOverdue(currentDate) -> BillStatus.OVERDUE
    else -> BillStatus.PENDING
}

fun Subscription.markAsPaid(): Subscription = copy(
    status = BillStatus.PAID,
    dueDate = dueDate.nextBillingDate(paymentFrequency)
)

fun Subscription.skipCycle(): Subscription = copy(
    dueDate = dueDate.nextBillingDate(paymentFrequency)
)

private val AVERAGE_DAYS_PER_MONTH = BigDecimal("30.44") // 365.0 / 12

fun List<Subscription>.totalMonthlyCost(): Money? {
    if (isEmpty()) return null
    val currencies = map { it.amount.currency }.toSet()
    if (currencies.size > 1) return null
    val total = sumOf { it.monthlyNormalizedAmount() }
    return Money(total, currencies.first())
}

fun List<Subscription>.averageDailyCost(): Money? {
    return totalMonthlyCost()?.let { total ->
        Money(
            amount = total.amount.divide(AVERAGE_DAYS_PER_MONTH, 2, RoundingMode.HALF_UP),
            currency = total.currency
        )
    }
}

fun Subscription.daysUntilTrialEnd(currentDate: LocalDate): Long? {
    return trialEndDate?.let { ChronoUnit.DAYS.between(currentDate, it) }
}

fun Subscription.convertToPaid(): Subscription = copy(
    status = BillStatus.PENDING,
    trialEndDate = null
)

