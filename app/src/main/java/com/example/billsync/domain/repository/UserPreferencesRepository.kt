package com.example.billsync.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userName: Flow<String?>
    suspend fun setUserName(name: String)

    val lastUsedCurrencyCode: Flow<String?>
    suspend fun setLastUsedCurrency(currencyCode: String)

    val userDefaultCurrencyCode: Flow<String?>
    suspend fun setUserDefaultCurrency(currencyCode: String)
    suspend fun clearUserDefaultCurrency()
}