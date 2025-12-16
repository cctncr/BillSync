package com.example.billsync.presentation.extensions

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

fun String.toComposeColor(): Color {
    return try {
        Color(this.toColorInt())
    } catch (e: IllegalArgumentException) {
        Color.Gray
    }
}