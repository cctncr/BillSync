package com.example.billsync.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val userName: Flow<String?>
    suspend fun setUserName(name: String)

    val defaultCurrencyCode: Flow<String?>
    suspend fun setDefaultCurrency(currencyCode: String)
}