package com.example.billsync.presentation.extensions

import android.content.Context
import com.example.billsync.R
import com.example.billsync.domain.model.PaymentFrequency

fun PaymentFrequency.toDisplayName(context: Context): String {
    return when (this) {
        PaymentFrequency.WEEKLY -> context.getString(R.string.payment_frequency_weekly)
        PaymentFrequency.MONTHLY -> context.getString(R.string.payment_frequency_monthly)
        PaymentFrequency.QUARTERLY -> context.getString(R.string.payment_frequency_quarterly)
        PaymentFrequency.YEARLY -> context.getString(R.string.payment_frequency_yearly)
        PaymentFrequency.ONE_TIME -> context.getString(R.string.payment_frequency_one_time)
    }
}

fun PaymentFrequency.toAbbreviation(context: Context): String {
    return when (this) {
        PaymentFrequency.WEEKLY -> context.getString(R.string.payment_frequency_weekly_abbr)
        PaymentFrequency.MONTHLY -> context.getString(R.string.payment_frequency_monthly_abbr)
        PaymentFrequency.QUARTERLY -> context.getString(R.string.payment_frequency_quarterly_abbr)
        PaymentFrequency.YEARLY -> context.getString(R.string.payment_frequency_yearly_abbr)
        PaymentFrequency.ONE_TIME -> context.getString(R.string.payment_frequency_one_time_abbr)
    }
}