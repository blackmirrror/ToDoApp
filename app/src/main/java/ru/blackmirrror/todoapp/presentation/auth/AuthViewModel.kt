package ru.blackmirrror.todoapp.presentation.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.blackmirrror.todoapp.data.repositories.AuthRepository
import ru.blackmirrror.todoapp.data.utils.UiState


class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isRegister = MutableLiveData<UiState<String>>()
    val isRegister: LiveData<UiState<String>> = _isRegister

    private val _isLogin = MutableLiveData<UiState<String>>()
    val isLogin: LiveData<UiState<String>> = _isLogin

    fun login(email: String, password: String) {
        authRepository.loginUser(email, password) {
            _isLogin.value = it
        }
    }

    fun register(email: String, password: String) {
        authRepository.registerUser(email, password) {
            _isRegister.value = it
        }
    }
}