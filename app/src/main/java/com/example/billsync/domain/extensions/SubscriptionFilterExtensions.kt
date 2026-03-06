package com.example.billsync.domain.extensions

import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Filter
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import java.time.LocalDate

fun List<Subscription>.applyStatusFilter(
    filter: Filter<BillStatus>,
    currentDate: LocalDate
): List<Subscription> = when (filter) {
    is Filter.All -> this
    is Filter.Specific -> this.filter { it.effectiveStatus(currentDate) == filter.value }
}

fun List<Subscription>.applyFrequencyFilter(
    filter: Filter<PaymentFrequency>
): List<Subscription> = when (filter) {
    is Filter.All -> this
    is Filter.Specific -> this.filter { it.paymentFrequency == filter.value }
}

fun List<Subscription>.applyFilters(
    statusFilter: Filter<BillStatus>,
    frequencyFilter: Filter<PaymentFrequency>,
    currentDate: LocalDate
): List<Subscription> =
    this.applyStatusFilter(statusFilter, currentDate)
        .applyFrequencyFilter(frequencyFilter)
