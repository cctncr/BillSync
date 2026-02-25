package com.example.billsync.presentation.screen.create_subscription.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.billsync.R
import java.util.Currency

@Composable
fun AmountSelectionCard(
    amount: String,
    onAmountChange: (String) -> Unit,
    selectedCurrency: Currency,
    onCurrencyPickerClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        OutlinedTextField(
            value = amount,
            onValueChange = onAmountChange,
            label = { Text(stringResource(R.string.amount_label)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.weight(1f)
        )
        OutlinedButton(onClick = onCurrencyPickerClick) {
            Text("${selectedCurrency.symbol} ${selectedCurrency.currencyCode}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmountSelectionCard_Preview() {
    AmountSelectionCard(
        amount = "0.00",
        onAmountChange = { },
        selectedCurrency = Currency.getInstance("USD"),
        onCurrencyPickerClick = { }
    )
}