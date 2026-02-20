package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import com.example.billsync.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Composable
fun LocalDate.toDueDateMessage(currentDate: LocalDate): String {
    val daysUntil = ChronoUnit.DAYS.between(currentDate, this).toInt()
    return when {
        daysUntil == 0 -> stringResource(R.string.due_today)
        daysUntil == 1 -> stringResource(R.string.due_tomorrow)
        daysUntil > 0 -> pluralStringResource(R.plurals.days_left, daysUntil, daysUntil)
        else -> pluralStringResource(R.plurals.overdue_days, -daysUntil, -daysUntil)
    }
}