package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.billsync.data.local.datastore.UserPreferencesDataSource
import com.example.billsync.domain.extensions.applyFilters
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Filter
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.domain.repository.SubscriptionRepository
import com.example.billsync.presentation.mapper.toUi
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.domain.model.Subscription as DomainSubscription
import com.example.billsync.presentation.state.SubscriptionsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SubscriptionViewModel @Inject constructor(
    private val repository: SubscriptionRepository,
    private val userPreferencesDataSource: UserPreferencesDataSource
) : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionsUiState())
    val uiState: StateFlow<SubscriptionsUiState> = _uiState.asStateFlow()

    private var allSubscriptions: List<DomainSubscription> = emptyList()

    private var currentStatusFilter: Filter<BillStatus> = Filter.All()
    private var currentFrequencyFilter: Filter<PaymentFrequency> = Filter.All()
    private var currentSortOption: BillSortOption = BillSortOption.REVENUE_DATE_ASC

    init {
        updateFilterOptions()
        observeSubscriptions()
        observeUserName()
    }

    private fun observeSubscriptions() {
        viewModelScope.launch {
            repository.getAllSubscriptions().collect { subscriptions ->
                allSubscriptions = subscriptions
                applyFiltersAndSort()
            }
        }
    }

    private fun observeUserName() {
        viewModelScope.launch {
            userPreferencesDataSource.userName.collect { name ->
                _uiState.update { it.copy(userName = name) }
            }
        }
    }

    fun onStatusFilterSelected(option: FilterOption<BillStatus>) {
        currentStatusFilter = when (option) {
            is FilterOption.All -> Filter.All()
            is FilterOption.Specific -> Filter.Specific(option.value)
        }
        updateFilterOptions()
        applyFiltersAndSort()
    }

    fun onFrequencyFilterSelected(option: FilterOption<PaymentFrequency>) {
        currentFrequencyFilter = when (option) {
            is FilterOption.All -> Filter.All()
            is FilterOption.Specific -> Filter.Specific(option.value)
        }
        updateFilterOptions()
        applyFiltersAndSort()
    }

    fun onSortOptionSelected(option: BillSortOption) {
        currentSortOption = option
        _uiState.update { it.copy(currentSortOption = option) }
        applyFiltersAndSort()
    }

    private fun updateFilterOptions() {
        _uiState.update { currentState ->
            currentState.copy(
                statusFilterOptions = createStatusFilterOptions(),
                frequencyFilterOptions = createFrequencyFilterOptions()
            )
        }
    }

    private fun createStatusFilterOptions(): List<FilterOption<BillStatus>> = buildList {
        add(FilterOption.All(isSelected = currentStatusFilter is Filter.All))
        BillStatus.entries.forEach { status ->
            add(
                FilterOption.Specific(
                    value = status,
                    isSelected = (currentStatusFilter as? Filter.Specific)?.value == status
                )
            )
        }
    }

    private fun createFrequencyFilterOptions(): List<FilterOption<PaymentFrequency>> = buildList {
        add(FilterOption.All(isSelected = currentFrequencyFilter is Filter.All))
        PaymentFrequency.entries.forEach { frequency ->
            add(
                FilterOption.Specific(
                    value = frequency,
                    isSelected = (currentFrequencyFilter as? Filter.Specific)?.value == frequency
                )
            )
        }
    }

    private fun sortSubscriptions(
        list: List<DomainSubscription>,
        sortOption: BillSortOption
    ): List<DomainSubscription> {
        return when (sortOption) {
            BillSortOption.REVENUE_DATE_ASC -> list.sortedBy { it.dueDate }
            BillSortOption.REVENUE_DATE_DESC -> list.sortedByDescending { it.dueDate }
            BillSortOption.AMOUNT_ASC -> list.sortedBy { it.amount.amount }
            BillSortOption.AMOUNT_DESC -> list.sortedByDescending { it.amount.amount }
        }
    }

    private fun applyFiltersAndSort() {
        val filteredSubscriptions = allSubscriptions
            .applyFilters(
                statusFilter = currentStatusFilter,
                frequencyFilter = currentFrequencyFilter
            )
            .let { list -> sortSubscriptions(list, currentSortOption) }
            .map { it.toUi() }

        _uiState.update { currentState ->
            currentState.copy(
                subscriptions = filteredSubscriptions,
                activeSubCount = filteredSubscriptions.size,
                isLoading = false
            )
        }
    }
}
