package com.example.billsync.domain.model

enum class BillStatus(val displayName: String) {
    ALL("All"),
    PAID("Paid"),
    PENDING("Pending"),
    OVERDUE("Overdue")
}