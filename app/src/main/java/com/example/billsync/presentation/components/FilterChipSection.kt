package com.example.billsync.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.domain.model.BillStatus
import com.example.billsync.presentation.model.FilterOption
import com.example.billsync.domain.model.PaymentFrequency


@Composable
fun FilterChipSection(
    billStatusOptions: List<FilterOption>,
    paymentFrequencyOptions: List<FilterOption>,
    onBillStatusSelected: (FilterOption) -> Unit,
    onFrequencySelected: (FilterOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        FilterSectionTitle(
            text = stringResource(R.string.status),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 4.dp)
        )

        FilterChipRow(
            options = billStatusOptions,
            onFilterClick = { filter -> onBillStatusSelected(filter) }
        )

        Spacer(modifier.padding(vertical = 8.dp))

        FilterSectionTitle(
            text = stringResource(R.string.frequency),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 4.dp)
        )

        FilterChipRow(
            options = paymentFrequencyOptions,
            onFilterClick = { filter -> onFrequencySelected(filter) }
        )
    }
}

@Composable
fun FilterChipRow(
    options: List<FilterOption>,
    onFilterClick: (FilterOption) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        items(
            items = options,
            key = { it.id }
        ) { filter ->
            FilterChip(
                filter.isSelected,
                onClick = { onFilterClick(filter) },
                label = {
                    Text(
                        text = filter.label,
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

@Preview(showBackground = true)
@Composable
fun FilterChipSection_BillStatus_Preview() {
    FilterChipRow(
        options =
            buildList {
                BillStatus.entries.forEach { billStatus ->
                    add(
                        FilterOption(
                            billStatus.ordinal.toString(),
                            billStatus.displayName,
                            false
                        )
                    )
                }
            },
        onFilterClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipSection_PaymentFrequency_Preview() {
    FilterChipRow(
        options =
            buildList {
                PaymentFrequency.entries.forEach { billStatus ->
                    add(
                        FilterOption(
                            billStatus.ordinal.toString(),
                            billStatus.displayName,
                            false
                        )
                    )
                }
            },
        onFilterClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun FilterChipSection_Preview() {
    FilterChipSection(
        billStatusOptions = buildList {
            BillStatus.entries.forEach { billStatus ->
                add(
                    FilterOption(
                        billStatus.ordinal.toString(),
                        billStatus.displayName,
                        false
                    )
                )
            }
        },
        paymentFrequencyOptions = buildList {
            PaymentFrequency.entries.forEach { billStatus ->
                add(
                    FilterOption(
                        billStatus.ordinal.toString(),
                        billStatus.displayName,
                        false
                    )
                )
            }
        },
        onBillStatusSelected = { },
        onFrequencySelected = { }
    )
}