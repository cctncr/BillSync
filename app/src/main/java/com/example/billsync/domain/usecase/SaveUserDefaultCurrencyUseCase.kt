package com.example.billsync.domain.usecase

import com.example.billsync.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveUserDefaultCurrencyUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(currencyCode: String) {
        userPreferencesRepository.setUserDefaultCurrency(currencyCode)
    }
}
