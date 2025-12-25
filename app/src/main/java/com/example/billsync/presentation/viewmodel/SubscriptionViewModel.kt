package com.example.billsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.billsync.domain.extensions.applyFilters
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.Filter
import com.example.billsync.domain.model.Money
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.extensions.formatForDisplay
import com.example.billsync.presentation.mapper.toUi
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.domain.model.Subscription as DomainSubscription
import com.example.billsync.presentation.state.SubscriptionsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.time.LocalDate
import java.util.Currency
import java.util.Locale

class SubscriptionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SubscriptionsUiState())
    val uiState: StateFlow<SubscriptionsUiState> = _uiState.asStateFlow()

    private val allSubscriptions: List<DomainSubscription> = createMockData()

    private var currentStatusFilter: Filter<BillStatus> = Filter.All()
    private var currentFrequencyFilter: Filter<PaymentFrequency> = Filter.All()
    private var currentSortOption: BillSortOption = BillSortOption.REVENUE_DATE_ASC

    init {
        updateFilterOptions()
        applyFiltersAndSort()
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
                activeSubCount = filteredSubscriptions.size
            )
        }
    }

    private fun createMockData(): List<DomainSubscription> = listOf(
        DomainSubscription(
            id = "1",
            brandName = "Netflix",
            amount = Money(
                BigDecimal("19.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().plusDays(5),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#E50914",
            brandIconId = null
        ),
        DomainSubscription(
            id = "2",
            brandName = "Spotify",
            amount = Money(
                BigDecimal("9.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().plusDays(12),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#1DB954",
            brandIconId = null
        ),
        DomainSubscription(
            id = "3",
            brandName = "Amazon Prime",
            amount = Money(
                BigDecimal("14.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().minusDays(2),
            status = BillStatus.OVERDUE,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#FF9900",
            brandIconId = null
        ),
        DomainSubscription(
            id = "4",
            brandName = "YouTube Premium",
            amount = Money(
                BigDecimal("11.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now(),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#FF0000",
            brandIconId = null
        ),
        DomainSubscription(
            id = "5",
            brandName = "Adobe Creative Cloud",
            amount = Money(
                BigDecimal("54.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().minusDays(10),
            status = BillStatus.OVERDUE,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#FF0000",
            brandIconId = null
        ),
        DomainSubscription(
            id = "6",
            brandName = "GitHub Pro",
            amount = Money(
                BigDecimal("7.00"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().plusDays(20),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.MONTHLY,
            brandColorHex = "#181717",
            brandIconId = null
        ),
        DomainSubscription(
            id = "7",
            brandName = "Apple Music",
            amount = Money(
                BigDecimal("9.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().plusDays(7),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.WEEKLY,
            brandColorHex = "#FA243C",
            brandIconId = null
        ),
        DomainSubscription(
            id = "8",
            brandName = "Dropbox Plus",
            amount = Money(
                BigDecimal("11.99"),
                Currency.getInstance(Locale.getDefault())
            ),
            dueDate = LocalDate.now().plusDays(60),
            status = BillStatus.PENDING,
            paymentFrequency = PaymentFrequency.YEARLY,
            brandColorHex = "#0061FF",
            brandIconId = null
        )
    ).also { subscriptions ->
        _uiState.value = SubscriptionsUiState(
            userName = "Alex Adams",
            userIcon = null,
            totalBalance = Money(
                amount = BigDecimal("80.49"),
                currencyCode = Currency.getInstance(Locale.getDefault())
            ).formatForDisplay(),
            avgDailyCost = Money(
                amount = BigDecimal("4.90"),
                currencyCode = Currency.getInstance(Locale.getDefault())
            ).formatForDisplay(),
            subscriptions = subscriptions.map { it.toUi() },
            isLoading = false,
            error = null
        )
    }
}
