package com.example.billsync.presentation.screen.create_subscription.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import com.example.billsync.domain.model.PaymentFrequency
import com.example.billsync.presentation.extensions.toDisplayName

@Composable
fun FrequencySelector(
    selectedFrequency: PaymentFrequency,
    onFrequencySelected: (PaymentFrequency) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.frequency),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            PaymentFrequency.entries.forEach { frequency ->
                FilterChip(
                    selected = frequency == selectedFrequency,
                    onClick = { onFrequencySelected(frequency) },
                    label = { Text(frequency.toDisplayName()) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FrequencySelector_Preview() {
    FrequencySelector(
        selectedFrequency = PaymentFrequency.MONTHLY,
        onFrequencySelected =  { }
    )
}