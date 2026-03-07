package com.example.billsync.presentation.screen.create_subscription.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import java.time.LocalDate

@Composable
fun TrialToggleField(
    isTrialEnabled: Boolean,
    trialEndDate: LocalDate?,
    onTrialToggle: (Boolean) -> Unit,
    onTrialEndDateChange: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(stringResource(R.string.trial_toggle_label))
            Switch(
                checked = isTrialEnabled,
                onCheckedChange = onTrialToggle
            )
        }

        if (isTrialEnabled) {
            TrialEndDateField(
                trialEndDate = trialEndDate ?: LocalDate.now(),
                onDateSelected = onTrialEndDateChange
            )
        }
    }

}

@Composable
private fun TrialEndDateField(
    trialEndDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = trialEndDate.toEpochDay().times(86400000L)
    )

    OutlinedTextField(
        value = trialEndDate.toString(),
        onValueChange = { },
        readOnly = true,
        label = { Text(stringResource(R.string.trial_end_date_label)) },
        trailingIcon = {
            IconButton(onClick = { showDatePicker = true }) {
                Icon(Icons.Default.DateRange, contentDescription = null)
            }
        },
        modifier = modifier.fillMaxWidth()
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            onDateSelected(LocalDate.ofEpochDay(millis / 86400000L))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text(stringResource(R.string.save))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
