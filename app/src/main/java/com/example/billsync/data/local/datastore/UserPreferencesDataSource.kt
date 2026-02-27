package com.example.billsync.data.local.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.billsync.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataSource @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : UserPreferencesRepository {
    private companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val DEFAULT_CURRENCY_CODE = stringPreferencesKey("default_currency_code")
    }

    override val userName: Flow<String?> = dataStore.data.map { preferences ->
        preferences[USER_NAME]
    }

    override val defaultCurrencyCode: Flow<String?> = dataStore.data.map { preferences ->
        preferences[DEFAULT_CURRENCY_CODE]
    }

    override suspend fun setUserName(name: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    override suspend fun setDefaultCurrency(currencyCode: String) {
        dataStore.edit { preferences ->
            preferences[DEFAULT_CURRENCY_CODE] = currencyCode
        }
    }
}