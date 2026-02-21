package com.example.billsync.data.repository

import com.example.billsync.data.local.dao.SubscriptionDao
import com.example.billsync.data.local.entity.SubscriptionEntity
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Money
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency
import javax.inject.Inject

class SubscriptionRepositoryImpl @Inject constructor(
    private val dao: SubscriptionDao
) : SubscriptionRepository {

    override fun getAllSubscriptions(): Flow<List<Subscription>> {
        return dao.getAllSubscriptions().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getSubscriptionById(id: String): Subscription? {
        return dao.getSubscriptionById(id)?.toDomain()
    }

    override suspend fun insertSubscription(subscription: Subscription) {
        dao.upsertSubscription(subscription.toEntity())
    }

    override suspend fun updateSubscription(subscription: Subscription) {
        dao.updateSubscription(subscription.toEntity())
    }

    override suspend fun deleteSubscription(id: String) {
        dao.deleteSubscriptionById(id)
    }
}

private fun SubscriptionEntity.toDomain(): Subscription = Subscription(
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

private fun Subscription.toEntity(): SubscriptionEntity = SubscriptionEntity(
    id = id,
    brandName = brandName,
    amountValue = amount.amount.toDouble(),
    currencyCode = amount.currency.currencyCode,
    dueDateEpochDay = dueDate.toEpochDay(),
    status = status.name,
    paymentFrequency = paymentFrequency.name,
    brandColorHex = brandColorHex,
    brandIconId = brandIconId
)