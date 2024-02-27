package ru.blackmirrror.todoapp.presentation.todoItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import kotlinx.coroutines.flow.map
import ru.blackmirrror.todoapp.data.Task
import ru.blackmirrror.todoapp.data.repositories.AuthRepository
import ru.blackmirrror.todoapp.data.repositories.TaskRepository

class TodoItemsViewModel(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _uid = MutableLiveData<String?>()
    val uid: LiveData<String?> = _uid

    private val _tasks = MutableLiveData<List<Task>?>()
    val tasks: LiveData<List<Task>?> = _tasks

    val countCompletedTasks: LiveData<Int> = _tasks.map { items -> items?.count { it.completed } ?: 0 }

    private val _isVisibleCompleted = MutableLiveData(true)
    val isVisibleCompleted: LiveData<Boolean> = _isVisibleCompleted

    init {
        checkLoggedInUser()
    }

    private fun checkLoggedInUser() {
        _uid.postValue(authRepository.getCurrentUserUid())
    }

    fun loadTasks() {
        taskRepository.getAllTask(_tasks)
    }

    fun logout() {
        authRepository.logoutUser()
        _uid.postValue(null)
    }

    fun changeCompleted(id: String, completed: Boolean) {
        taskRepository.changeStatus(id, completed)
    }

    fun changeVisibilityCompleted() {
        _isVisibleCompleted.postValue(!_isVisibleCompleted.value!!)
        if (_isVisibleCompleted.value!!)
            taskRepository.getNotCompletedTask(_tasks)
        else
            taskRepository.getAllTask(_tasks)
    }
}