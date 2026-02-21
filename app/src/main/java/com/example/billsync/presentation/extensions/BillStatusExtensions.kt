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

@Composable
fun BillStatus.getStatusColor(): Color {
    return when (this) {
        BillStatus.PAID -> Color(0xFF4CAF50)
        BillStatus.PENDING -> Color(0xFFFF9800)
        BillStatus.OVERDUE -> Color(0xFFEF5350)
    }
}