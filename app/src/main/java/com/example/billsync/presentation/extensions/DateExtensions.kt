package com.example.billsync.presentation.extensions

import android.content.Context
import com.example.billsync.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun LocalDate.toDueDateMessage(context: Context, currentDate: LocalDate): String {
    val daysUntil = ChronoUnit.DAYS.between(currentDate, this)
    return when {
        daysUntil < 0 -> context.resources.getQuantityString(
            R.plurals.overdue_days, (-daysUntil).toInt(), -daysUntil
        )

        daysUntil == 0L -> context.getString(R.string.due_today)
        daysUntil == 1L -> context.getString(R.string.due_tomorrow)
        else -> context.resources.getQuantityString(
            R.plurals.days_left, daysUntil.toInt(), daysUntil
        )
    }
}