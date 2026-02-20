package com.example.billsync.domain.model

sealed interface Filter<out T> {
    class All<T> : Filter<T>
    data class Specific<T>(val value: T) : Filter<T>
}