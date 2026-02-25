package com.example.billsync.data.mapper

import com.example.billsync.data.local.entity.SubscriptionEntity
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Money
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency

internal fun SubscriptionEntity.toDomain(): Subscription = Subscription(
    id = id,
    brandName = brandName,
    amount = Money(
        amount = BigDecimal(amountValue),
        currency = Currency.getInstance(currencyCode)
    ),
    dueDate = LocalDate.ofEpochDay(dueDateEpochDay),
    status = BillStatus.entries.find { it.name == status } ?: BillStatus.PENDING,
    paymentFrequency = PaymentFrequency.entries.find { it.name == paymentFrequency } ?: PaymentFrequency.MONTHLY,
    brandColorHex = brandColorHex,
    brandIconId = brandIconId,
)

internal fun Subscription.toEntity(): SubscriptionEntity = SubscriptionEntity(
    id = id,
    brandName = brandName,
    amountValue = amount.amount.toPlainString(),
    currencyCode = amount.currency.currencyCode,
    dueDateEpochDay = dueDate.toEpochDay(),
    status = status.name,
    paymentFrequency = paymentFrequency.name,
    brandColorHex = brandColorHex,
    brandIconId = brandIconId
)