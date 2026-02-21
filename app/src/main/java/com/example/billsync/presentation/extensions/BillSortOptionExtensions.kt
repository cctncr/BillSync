package com.example.billsync.presentation.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.billsync.R
import com.example.billsync.domain.model.BillSortOption

@Composable
fun BillSortOption.toDisplayName(): String {
    return when (this) {
        BillSortOption.REVENUE_DATE_ASC -> stringResource(R.string.sort_option_date_asc)
        BillSortOption.REVENUE_DATE_DESC -> stringResource(R.string.sort_option_date_desc)
        BillSortOption.AMOUNT_ASC -> stringResource(R.string.sort_option_amount_asc)
        BillSortOption.AMOUNT_DESC -> stringResource(R.string.sort_option_amount_desc)
    }
}

@Composable
fun BillSortOption.toLabel(): String {
    return when (this) {
        BillSortOption.REVENUE_DATE_ASC -> stringResource(R.string.sort_option_date_asc_label)
        BillSortOption.REVENUE_DATE_DESC -> stringResource(R.string.sort_option_date_desc_label)
        BillSortOption.AMOUNT_ASC -> stringResource(R.string.sort_option_amount_asc_label)
        BillSortOption.AMOUNT_DESC -> stringResource(R.string.sort_option_amount_desc_label)
    }
}