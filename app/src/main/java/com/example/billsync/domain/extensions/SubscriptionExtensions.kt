package com.example.billsync.domain.extensions

import com.example.billsync.domain.model.Subscription
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun Subscription.daysUntilDue(currentDate: LocalDate): Long {
    return ChronoUnit.DAYS.between(currentDate, dueDate)
}

fun Subscription.isOverdue(currentDate: LocalDate): Boolean {
    return daysUntilDue(currentDate) < 0
}