package com.example.billsync.domain.model

enum class BillStatus {
    PAID,
    PENDING,
    OVERDUE,
    TRIAL;

    companion object {
        val selectableValues: List<BillStatus> = entries.filter { it != OVERDUE && it != TRIAL }
    }
}
