package ru.blackmirrror.todoapp.presentation.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.blackmirrror.todoapp.data.Task
import ru.blackmirrror.todoapp.data.repositories.TaskRepository

class EditViewModel(
    private val taskRepository: TaskRepository
): ViewModel() {

    private val _task = MutableLiveData<Task?>()
    val task: LiveData<Task?> = _task

    fun getTask(id: String) {
        taskRepository.getTask(id, _task)
    }

    fun deleteTask(id: String) {
        taskRepository.deleteTask(id)
    }

    fun saveTask(task: Task) {
        if (task.id != "")
            updateTask(task)
        else
            createTask(task)
    }

    private fun createTask(task: Task) {
        taskRepository.addTask(task)
    }

    private fun updateTask(task: Task) {
        taskRepository.updateTask(task)
    }
}