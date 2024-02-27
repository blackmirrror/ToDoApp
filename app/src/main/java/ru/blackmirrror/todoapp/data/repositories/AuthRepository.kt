package ru.blackmirrror.todoapp.data.repositories

import ru.blackmirrror.todoapp.data.utils.UiState

interface AuthRepository {
    fun loginUser(email: String, password: String, result: (UiState<String>) -> Unit)
    fun registerUser(
        email: String,
        password: String,
        result: (UiState<String>) -> Unit
    )
    fun getCurrentUserUid(): String?
    fun logoutUser()
}