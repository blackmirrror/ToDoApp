package ru.blackmirrror.todoapp.data.utils

sealed class UiState<out T> {
    data class Success<out T>(val data: T): UiState<T>()
    data class Failure(val error: String): UiState<String>()
}