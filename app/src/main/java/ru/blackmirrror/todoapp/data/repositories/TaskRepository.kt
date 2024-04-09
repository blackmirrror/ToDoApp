package ru.blackmirrror.todoapp.data.repositories

import androidx.lifecycle.MutableLiveData
import ru.blackmirrror.todoapp.data.Task

/**
 * @brief репозиторий задач
 */

interface TaskRepository {
    fun getAllTask(liveData: MutableLiveData<List<Task>?>)
    fun getNotCompletedTask(liveData: MutableLiveData<List<Task>?>)
    fun getTask(id: String, liveData: MutableLiveData<Task?>)
    fun addTask(task: Task): Boolean
    fun changeStatus(taskId: String, completed: Boolean)
    fun updateTask(task: Task)
    fun deleteTask(id: String)
}