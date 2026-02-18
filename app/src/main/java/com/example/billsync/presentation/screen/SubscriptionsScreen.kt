package com.example.billsync.presentation.screen

import android.annotation.SuppressLint
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.presentation.components.FilterChipSection
import com.example.billsync.presentation.components.ProfileCard
import com.example.billsync.presentation.components.SubscriptionCard
import com.example.billsync.presentation.components.TotalBalanceCard
import com.example.billsync.presentation.viewmodel.SubscriptionViewModel

@Composable
fun SubscriptionScreen(
    viewModel: SubscriptionViewModel,
    onSubscriptionCardClick: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

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
                    fullName = uiState.userName,
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
                    onStatusFilterSelected = viewModel::onStatusFilterSelected,
                    onFrequencyFilterSelected = viewModel::onFrequencyFilterSelected,
                    onSortOptionSelected = viewModel::onSortOptionSelected,
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

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun SubscriptionScreen_Preview() {
    SubscriptionScreen(
        viewModel = SubscriptionViewModel()
    ) { }
}