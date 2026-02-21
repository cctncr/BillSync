package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.model.FilterOption

@Composable
@JvmName("billStatusFilterOptionToLabel")
fun FilterOption<BillStatus>.toLabel(): String = when (this) {
    is FilterOption.All -> stringResource(R.string.filter_all)
    is FilterOption.Specific -> value.toDisplayName()
}

@Composable
@JvmName("paymentFrequencyFilterOptionToLabel")
fun FilterOption<PaymentFrequency>.toLabel(): String = when (this) {
    is FilterOption.All -> stringResource(R.string.filter_all)
    is FilterOption.Specific -> value.toDisplayName()
}