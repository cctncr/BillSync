package com.example.billsync.presentation.screen.subscriptions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.example.billsync.presentation.screen.subscriptions.components.FilterChipSection
import com.example.billsync.presentation.screen.subscriptions.components.ProfileCard
import com.example.billsync.presentation.screen.subscriptions.components.SubscriptionCard
import com.example.billsync.presentation.screen.subscriptions.components.TotalBalanceCard
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.presentation.preview.SubscriptionPreviewProvider
import com.example.billsync.presentation.state.SubscriptionsUiState
import com.example.billsync.presentation.viewmodel.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    viewModel: SubscriptionViewModel = hiltViewModel(),
    onSubscriptionCardClick: (String) -> Unit,
    onAddClick: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    SubscriptionContent(
        uiState = uiState,
        onSubscriptionCardClick = onSubscriptionCardClick,
        onStatusFilterSelected = viewModel::onStatusFilterSelected,
        onFrequencyFilterSelected = viewModel::onFrequencyFilterSelected,
        onSortOptionSelected = viewModel::onSortOptionSelected,
        onAddClick = onAddClick
    )
}

@Composable
private fun SubscriptionContent(
    uiState: SubscriptionsUiState,
    onSubscriptionCardClick: (String) -> Unit,
    onStatusFilterSelected: (FilterOption<BillStatus>) -> Unit,
    onFrequencyFilterSelected: (FilterOption<PaymentFrequency>) -> Unit,
    onSortOptionSelected: (BillSortOption) -> Unit,
    onAddClick: () -> Unit
) {
    val listState = rememberLazyListState()
    val isExpanded by remember { derivedStateOf { listState.firstVisibleItemIndex == 0 } }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                expanded = isExpanded,
                onClick = onAddClick,
                icon = { Icon(Icons.Default.Add, contentDescription = null) },
                text = { Text(stringResource(R.string.add_subscription)) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = listState,
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
                    greeting = stringResource(R.string.greeting_welcome_back),
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
                    balanceLabel = stringResource(R.string.balance_label_monthly),
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
        onAddClick = { }
    )
}