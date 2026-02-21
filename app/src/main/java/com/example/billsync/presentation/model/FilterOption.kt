package com.example.billsync.presentation.model

sealed interface FilterOption<T> {
    val isSelected: Boolean

    data class All<T>(
        override val isSelected: Boolean = false
    ) : FilterOption<T>

    data class Specific<T>(
        val value: T,
        override val isSelected: Boolean = false
    ) : FilterOption<T>
}