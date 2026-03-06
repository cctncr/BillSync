package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.example.billsync.R

@Composable
fun Long.toDueDateLabel(): String {
    val days = this.toInt()
    return when {
        days == 0 -> stringResource(R.string.due_today)
        days == 1 -> stringResource(R.string.due_tomorrow)
        days > 0 -> pluralStringResource(R.plurals.days_left, days, days)
        else -> pluralStringResource(R.plurals.overdue_days, -days, -days)
    }
}
