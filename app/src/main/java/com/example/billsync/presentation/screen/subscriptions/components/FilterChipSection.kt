package com.example.billsync.presentation.screen.subscriptions.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.domain.model.BillSortOption
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.extensions.toDisplayName
import com.example.billsync.presentation.extensions.toLabel
import com.example.billsync.presentation.model.FilterOption

@Composable
fun FilterChipSection(
    statusFilterOptions: List<FilterOption<BillStatus>>,
    frequencyFilterOptions: List<FilterOption<PaymentFrequency>>,
    currentSortOption: BillSortOption,
    onStatusFilterSelected: (FilterOption<BillStatus>) -> Unit,
    onFrequencyFilterSelected: (FilterOption<PaymentFrequency>) -> Unit,
    onSortOptionSelected: (BillSortOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                FilterSectionTitle(
                    text = stringResource(R.string.status),
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 4.dp)
                )

                StatusFilterChipRow(
                    options = statusFilterOptions,
                    onFilterClick = onStatusFilterSelected
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                FilterSectionTitle(
                    text = stringResource(R.string.sorting_by),
                    modifier = Modifier
                )

                SortActionMenu(
                    currentSelectedOption = currentSortOption,
                    onSortOptionSelected = onSortOptionSelected,
                )
            }
        }

        Spacer(Modifier.padding(vertical = 8.dp))

        FilterSectionTitle(
            text = stringResource(R.string.frequency),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 4.dp)
        )

        FrequencyFilterChipRow(
            options = frequencyFilterOptions,
            onFilterClick = onFrequencyFilterSelected
        )
    }
}

@Composable
private fun StatusFilterChipRow(
    options: List<FilterOption<BillStatus>>,
    onFilterClick: (FilterOption<BillStatus>) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(
            items = options,
            key = { option ->
                when (option) {
                    is FilterOption.All -> "status_all"
                    is FilterOption.Specific -> "status_${option.value.name}"
                }
            }
        ) { option ->
            FilterChip(
                selected = option.isSelected,
                onClick = { onFilterClick(option) },
                label = {
                    Text(
                        text = option.toLabel(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}

@Composable
private fun FrequencyFilterChipRow(
    options: List<FilterOption<PaymentFrequency>>,
    onFilterClick: (FilterOption<PaymentFrequency>) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(
            items = options,
            key = { option ->
                when (option) {
                    is FilterOption.All -> "frequency_all"
                    is FilterOption.Specific -> "frequency_${option.value.name}"
                }
            }
        ) { option ->
            FilterChip(
                selected = option.isSelected,
                onClick = { onFilterClick(option) },
                label = {
                    Text(
                        text = option.toLabel(),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            )
        }
    }
}

@Composable
private fun FilterSectionTitle(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = modifier
    )
}

@Composable
private fun SortActionMenu(
    currentSelectedOption: BillSortOption,
    onSortOptionSelected: (BillSortOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    val iconRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "Arrow Rotation"
    )

    Box(modifier = modifier) {
        TextButton(onClick = { expanded = !expanded }) {
            Text(currentSelectedOption.toLabel())
            Icon(
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(16.dp)
                    .rotate(iconRotation)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            BillSortOption.entries.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = option.toDisplayName(),
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = if (option == currentSelectedOption) {
                                FontWeight.Bold
                            } else {
                                FontWeight.Normal
                            }
                        )
                    },
                    leadingIcon = {
                        if (option == currentSelectedOption) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                    onClick = {
                        onSortOptionSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SortActionMenu_Preview() {
    SortActionMenu(
        currentSelectedOption = BillSortOption.REVENUE_DATE_DESC,
        onSortOptionSelected = { },
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipSection_BillStatus_Preview() {
    StatusFilterChipRow(
        options = listOf(
            FilterOption.All(isSelected = true),
            FilterOption.Specific(value = BillStatus.PAID, isSelected = false),
            FilterOption.Specific(value = BillStatus.PENDING, isSelected = false),
            FilterOption.Specific(value = BillStatus.OVERDUE, isSelected = false)
        ),
        onFilterClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipSection_PaymentFrequency_Preview() {
    FrequencyFilterChipRow(
        options = listOf(
            FilterOption.All(isSelected = true),
            FilterOption.Specific(value = PaymentFrequency.WEEKLY, isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.MONTHLY, isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.QUARTERLY, isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.YEARLY, isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.ONE_TIME, isSelected = false)
        ),
        onFilterClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipSection_Preview() {
    FilterChipSection(
        statusFilterOptions = listOf(
            FilterOption.All(isSelected = true),
            FilterOption.Specific(value = BillStatus.PAID, isSelected = false),
            FilterOption.Specific(value = BillStatus.PENDING, isSelected = false),
            FilterOption.Specific(value = BillStatus.OVERDUE, isSelected = false)
        ),
        frequencyFilterOptions = listOf(
            FilterOption.All(isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.WEEKLY, isSelected = true),
            FilterOption.Specific(value = PaymentFrequency.MONTHLY, isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.YEARLY, isSelected = false),
            FilterOption.Specific(value = PaymentFrequency.ONE_TIME, isSelected = false)
        ),
        currentSortOption = BillSortOption.REVENUE_DATE_DESC,
        onStatusFilterSelected = { },
        onFrequencyFilterSelected = { },
        onSortOptionSelected = { }
    )
}