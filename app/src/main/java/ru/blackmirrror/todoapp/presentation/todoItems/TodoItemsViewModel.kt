package ru.blackmirrror.todoapp.presentation.todoItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.blackmirrror.todoapp.data.repositories.AuthRepository

class TodoItemsViewModel(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _userEmail = MutableLiveData<String?>()
    val userEmail: LiveData<String?> = _userEmail

    init {
        checkLoggedInUser()
    }

    private fun checkLoggedInUser() {
        _userEmail.postValue(authRepository.getCurrentUserEmail())
    }

    fun logout() {
        authRepository.logoutUser()
        _userEmail.postValue(null)
    }
}