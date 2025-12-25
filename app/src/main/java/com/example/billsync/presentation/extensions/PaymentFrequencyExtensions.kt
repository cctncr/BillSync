package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.billsync.R
import com.example.billsync.domain.model.PaymentFrequency

@Composable
fun PaymentFrequency.toDisplayName(): String {
    return when (this) {
        PaymentFrequency.WEEKLY -> stringResource(R.string.payment_frequency_weekly)
        PaymentFrequency.MONTHLY -> stringResource(R.string.payment_frequency_monthly)
        PaymentFrequency.QUARTERLY -> stringResource(R.string.payment_frequency_quarterly)
        PaymentFrequency.YEARLY -> stringResource(R.string.payment_frequency_yearly)
        PaymentFrequency.ONE_TIME -> stringResource(R.string.payment_frequency_one_time)
    }
}

@Composable
fun PaymentFrequency.toAbbreviation(): String {
    return when (this) {
        PaymentFrequency.WEEKLY -> stringResource(R.string.payment_frequency_weekly_abbr)
        PaymentFrequency.MONTHLY -> stringResource(R.string.payment_frequency_monthly_abbr)
        PaymentFrequency.QUARTERLY -> stringResource(R.string.payment_frequency_quarterly_abbr)
        PaymentFrequency.YEARLY -> stringResource(R.string.payment_frequency_yearly_abbr)
        PaymentFrequency.ONE_TIME -> stringResource(R.string.payment_frequency_one_time_abbr)
    }
}