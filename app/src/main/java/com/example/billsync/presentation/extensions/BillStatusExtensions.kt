package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus

@Composable
fun BillStatus.toDisplayName(): String = when (this) {
    BillStatus.PAID -> stringResource(R.string.bill_status_paid)
    BillStatus.PENDING -> stringResource(R.string.bill_status_pending)
    BillStatus.OVERDUE -> stringResource(R.string.bill_status_overdue)
}

private val PaidGreen = Color(0xFF4CAF50)
private val PendingAmber = Color(0xFFFF9800)
private val OverdueRed = Color(0xFFEF5350)

fun BillStatus.getStatusColor(): Color = when (this) {
    BillStatus.PAID -> PaidGreen
    BillStatus.PENDING -> PendingAmber
    BillStatus.OVERDUE -> OverdueRed
}
