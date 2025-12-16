package com.example.billsync.presentation.extensions

import android.content.Context
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus

fun BillStatus.toDisplayName(context: Context): String {
    return when(this) {
        BillStatus.PAID -> context.getString(R.string.bill_status_paid)
        BillStatus.PENDING -> context.getString(R.string.bill_status_pending)
        BillStatus.OVERDUE -> context.getString(R.string.bill_status_overdue)
    }
}