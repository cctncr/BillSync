package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.billsync.R
import com.example.billsync.domain.model.PaymentType

@Composable
fun PaymentType.toDisplayName(): String = when (this) {
    PaymentType.PAID -> stringResource(R.string.payment_type_paid)
    PaymentType.SKIPPED -> stringResource(R.string.payment_type_skipped)
}
