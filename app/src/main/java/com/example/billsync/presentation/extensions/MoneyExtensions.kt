package com.example.billsync.presentation.extensions

import com.example.billsync.domain.model.Money
import java.text.NumberFormat
import java.util.Locale

fun Money.formatForDisplay(locale: Locale = Locale.getDefault()): String {
    val formatter = NumberFormat.getCurrencyInstance(locale)
    formatter.currency = this.currencyCode
    return formatter.format(this.amount)
}