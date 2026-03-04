package com.example.billsync.domain.usecase

import com.example.billsync.domain.repository.UserPreferencesRepository
import javax.inject.Inject

class SaveUserNameUseCase @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke(name: String) {
        userPreferencesRepository.setUserName(name)
    }
}
