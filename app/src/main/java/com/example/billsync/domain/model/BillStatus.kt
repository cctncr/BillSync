package com.example.billsync.domain.model

enum class BillStatus {
    PAID,
    PENDING,
    OVERDUE;

    companion object {
        val selectableValues: List<BillStatus> = entries.filter { it != OVERDUE }
    }
}
