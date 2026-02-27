package com.example.billsync.presentation.screen.create_subscription.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import java.util.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerBottomSheet(
    currencies: List<Currency>,
    onCurrencySelected: (Currency) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss
    ) {
        LazyColumn {
            items(currencies) { currency ->
                ListItem(
                    headlineContent = { Text(currency.displayName) },
                    leadingContent = { Text(currency.symbol) },
                    trailingContent = { Text(currency.currencyCode) },
                    modifier = Modifier.clickable {
                        onCurrencySelected(currency)
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CurrencyPickerBottomSheet_Preview() {
    CurrencyPickerBottomSheet(
        currencies = listOf(
            Currency.getInstance("USD"),
            Currency.getInstance("EUR"),
            Currency.getInstance("GBP"),
            Currency.getInstance("TRY"),
            Currency.getInstance("JPY"),
        ),
        onCurrencySelected = { },
        onDismiss = { }
    )
}