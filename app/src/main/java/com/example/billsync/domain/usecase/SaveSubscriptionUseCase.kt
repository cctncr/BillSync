package com.example.billsync.domain.usecase

import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import com.example.billsync.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveSubscriptionUseCase @Inject constructor(
    private val repository: SubscriptionRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(subscription: Subscription) {
        repository.insertSubscription(subscription)
        userPreferencesRepository.setDefaultCurrency(subscription.amount.currency.currencyCode)
    }
}