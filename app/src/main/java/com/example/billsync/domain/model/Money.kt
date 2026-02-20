package com.example.billsync.domain.model

import java.math.BigDecimal
import java.util.Currency

data class Money(
    val amount: BigDecimal,
    val currencyCode: Currency
) {
    init {
        require(amount >= BigDecimal.ZERO) { "Amount can not be negative" }
    }
}