package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsync.domain.repository.UserPreferencesRepository
import com.example.billsync.domain.usecase.SaveUserNameUseCase
import com.example.billsync.presentation.state.SettingsNavigationEvent
import com.example.billsync.presentation.state.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
    private val saveUserNameUseCase: SaveUserNameUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        loadUserName()
    }

    private fun loadUserName() {
        viewModelScope.launch {
            val name = userPreferencesRepository.userName.first() ?: ""
            _uiState.update { it.copy(userName = name, isLoading = false) }
        }
    }

    fun onUserNameChange(name: String) {
        _uiState.update { it.copy(userName = name) }
    }

    fun onSave() {
        val name = _uiState.value.userName

        if (name.isBlank()) {
            _uiState.update { it.copy(error = "Name cannot be empty") } // TODO: Hardcoded string
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                saveUserNameUseCase(name)
                _uiState.update { it.copy(navigationEvent = SettingsNavigationEvent.NavigateBack) }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to save"
                    )
                } // TODO: Hardcoded string
            }
        }
    }

    fun onNavigationEventConsumed() {
        _uiState.update { it.copy(navigationEvent = null) }
    }
}
