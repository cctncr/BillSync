package com.example.billsync.presentation.common_components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.billsync.R
import java.util.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyPickerBottomSheet(
    currencies: List<Currency>,
    onCurrencySelected: (Currency) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    var query by remember { mutableStateOf("") }

    val filtered = currencies.filter { currency ->
        val q = query.trim().lowercase()
        q.isEmpty() ||
                currency.currencyCode.lowercase().contains(q) ||
                currency.displayName.lowercase().contains(q) ||
                currency.symbol.lowercase().contains(q)
    }

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text(stringResource(R.string.currency_search_hint))},
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 8.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(filtered, key = { it.currencyCode }) { currency ->
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
