package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.billsync.domain.model.Subscription
import com.example.billsync.domain.repository.SubscriptionRepository
import com.example.billsync.domain.usecase.MarkSubscriptionAsPaidUseCase
import com.example.billsync.domain.usecase.SkipBillingCycleUseCase
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
    private val markSubscriptionAsPaidUseCase: MarkSubscriptionAsPaidUseCase,
    private val skipBillingCycleUseCase: SkipBillingCycleUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val subscriptionId = savedStateHandle.toRoute<SubscriptionDetail>().subscriptionId

    private val _uiState = MutableStateFlow(SubscriptionDetailUiState())
    val uiState: StateFlow<SubscriptionDetailUiState> = _uiState.asStateFlow()

    private val _currentDomainSubscription = MutableStateFlow<Subscription?>(null)

    init {
        loadSubscription()
    }

    private fun loadSubscription() {
        viewModelScope.launch {
            repository.observeSubscriptionById(subscriptionId).collect { subscription ->
                if (subscription == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Subscription not found"
                        )
                    } // TODO: Hardcoded String
                } else {
                    _currentDomainSubscription.value = subscription

                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            subscription = subscription.toUi()
                        )
                    }
                }
            }
        }
    }

    fun onDelete() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                repository.deleteSubscription(subscriptionId)
                _uiState.update {
                    it.copy(
                        navigationEvent = SubscriptionDetailNavigationEvent.NavigateBack
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to delete"
                    )
                } // TODO: Hardcoded string
            }
        }
    }

    fun onNavigationEventConsumed() {
        _uiState.update { it.copy(navigationEvent = null) }
    }

    fun onMarkAsPaid() {
        val sub = _currentDomainSubscription.value ?: return
        viewModelScope.launch {
            try {
                markSubscriptionAsPaidUseCase(sub)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to mark as paid"
                    )
                } // TODO(Hardcoded string)
            }
        }
    }

    fun onSkipCycle() {
        val sub = _currentDomainSubscription.value ?: return
        viewModelScope.launch {
            try {
                skipBillingCycleUseCase(sub)
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Failed to skip cycle"
                    )
                } // TODO(Hardcoded string)
            }
        }
    }
}
