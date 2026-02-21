package com.example.billsync.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.billsync.R
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.components.FilterChipSection
import com.example.billsync.presentation.components.ProfileCard
import com.example.billsync.presentation.components.SubscriptionCard
import com.example.billsync.presentation.components.TotalBalanceCard
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.presentation.preview.SubscriptionPreviewProvider
import com.example.billsync.presentation.state.SubscriptionsUiState
import com.example.billsync.presentation.viewmodel.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    viewModel: SubscriptionViewModel = hiltViewModel(),
    onSubscriptionCardClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    SubscriptionContent(
        uiState = uiState,
        onSubscriptionCardClick = onSubscriptionCardClick,
        onStatusFilterSelected = viewModel::onStatusFilterSelected,
        onFrequencyFilterSelected = viewModel::onFrequencyFilterSelected,
        onSortOptionSelected = viewModel::onSortOptionSelected,
    )
}

@Composable
private fun SubscriptionContent(
    uiState: SubscriptionsUiState,
    onSubscriptionCardClick: (String) -> Unit,
    onStatusFilterSelected: (FilterOption<BillStatus>) -> Unit,
    onFrequencyFilterSelected: (FilterOption<PaymentFrequency>) -> Unit,
    onSortOptionSelected: (BillSortOption) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = innerPadding.calculateBottomPadding() + 16.dp,
                start = 0.dp,
                end = 0.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            item {
                ProfileCard(
                    fullName = uiState.userName ?: stringResource(R.string.default_user_name),
                    profileIcon = uiState.userIcon,
                    greeting = uiState.greetingText,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                TotalBalanceCard(
                    totalBalance = uiState.totalBalance,
                    avgDailyCost = uiState.avgDailyCost,
                    balanceLabel = uiState.balanceLabel,
                    activeSubsCount = uiState.activeSubCount,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                FilterChipSection(
                    statusFilterOptions = uiState.statusFilterOptions,
                    frequencyFilterOptions = uiState.frequencyFilterOptions,
                    currentSortOption = uiState.currentSortOption,
                    onStatusFilterSelected = onStatusFilterSelected,
                    onFrequencyFilterSelected = onFrequencyFilterSelected,
                    onSortOptionSelected = onSortOptionSelected,
                    modifier = Modifier.padding(bottom = 8.dp, end = 8.dp)
                )
            }

            items(
                items = uiState.subscriptions,
                key = { it.id }
            ) { subscription ->
                SubscriptionCard(
                    subscription = subscription,
                    onClick = { onSubscriptionCardClick(subscription.id) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SubscriptionContent_Preview(
    @PreviewParameter(SubscriptionPreviewProvider::class) uiState: SubscriptionsUiState
) {
    SubscriptionContent(
        uiState = uiState,
        onSubscriptionCardClick = { },
        onStatusFilterSelected = { },
        onFrequencyFilterSelected = { },
        onSortOptionSelected = { },
    )
}