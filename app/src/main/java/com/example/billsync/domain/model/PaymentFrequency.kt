package com.example.billsync.domain.model

enum class PaymentFrequency(val displayName: String) {
    ALL("All"),
    MONTHLY("Monthly"),
    QUARTERLY("Quarterly"),
    YEARLY("Yearly"),
    ONE_TIME("One Time")
}