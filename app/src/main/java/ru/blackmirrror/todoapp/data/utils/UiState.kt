package ru.blackmirrror.todoapp.data.utils

/**
 * @brief класс текущего состояния запроса
 */

sealed class UiState<out T> {
    data class Success<out T>(val data: T): UiState<T>()
    data class Failure(val error: String): UiState<String>()
}