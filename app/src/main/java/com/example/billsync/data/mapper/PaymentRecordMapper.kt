package com.example.billsync.data.mapper

import com.example.billsync.data.local.entity.PaymentRecordEntity
import com.example.billsync.domain.model.Money
import com.example.billsync.domain.model.PaymentRecord
import com.example.billsync.domain.model.PaymentType
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency

internal fun PaymentRecordEntity.toDomain(): PaymentRecord = PaymentRecord(
    id = id,
    subscriptionId = subscriptionId,
    recordedAt = LocalDate.ofEpochDay(recordedAtEpochDay),
    type = PaymentType.entries.find { it.name == type } ?: PaymentType.PAID,
    amount = if (amountValue != null && currencyCode != null) {
        Money(BigDecimal(amountValue), Currency.getInstance(currencyCode))
    } else null
)

internal fun PaymentRecord.toEntity(): PaymentRecordEntity = PaymentRecordEntity(
    id = id,
    subscriptionId = subscriptionId,
    recordedAtEpochDay =  recordedAt.toEpochDay(),
    type = type.name,
    amountValue = amount?.amount?.toPlainString(),
    currencyCode = amount?.currency?.currencyCode
)
