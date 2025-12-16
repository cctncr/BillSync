package com.example.billsync.presentation.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

data class Subscription(
    val id: String,
    val brandName: String,
    val displayAmount: String,
    val dueDateMessage: String,
    val status: String,
    val paymentFrequency: String,
    val brandColor: Color,
    val brandIcon: ImageVector?
)