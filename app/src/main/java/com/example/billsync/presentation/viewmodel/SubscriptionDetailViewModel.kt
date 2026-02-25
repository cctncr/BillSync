package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.billsync.domain.repository.SubscriptionRepository
import com.example.billsync.domain.usecase.DeleteSubscriptionUseCase
import com.example.billsync.presentation.mapper.toUi
import com.example.billsync.presentation.navigation.route.SubscriptionDetail
import com.example.billsync.presentation.state.SubscriptionDetailNavigationEvent
import com.example.billsync.presentation.state.SubscriptionDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionDetailViewModel @Inject constructor(
    private val repository: SubscriptionRepository,
    private val deleteSubscriptionUseCase: DeleteSubscriptionUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val subscriptionId = savedStateHandle.toRoute<SubscriptionDetail>().subscriptionId

    private val _uiState = MutableStateFlow(SubscriptionDetailUiState())
    val uiState: StateFlow<SubscriptionDetailUiState> = _uiState.asStateFlow()

    init {
        loadSubscription()
    }

    private fun loadSubscription() {
        viewModelScope.launch {
            val result = repository.getSubscriptionById(subscriptionId)
            if (result == null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Subscription not found"
                    )
                } // TODO: Hardcoded String
            } else {
                _uiState.update { it.copy(isLoading = false, subscription = result.toUi()) }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                deleteSubscriptionUseCase(subscriptionId)
                _uiState.update {
                    it.copy(
                        navigationEvent = SubscriptionDetailNavigationEvent.NavigateBack
                    )
                }
            } catch (_: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Failed to delete"
                    )
                } // TODO: Hardcoded string
            }
        }
    }

    fun onNavigationEventConsumed() {
        _uiState.update { it.copy(navigationEvent = null) }
    }
}