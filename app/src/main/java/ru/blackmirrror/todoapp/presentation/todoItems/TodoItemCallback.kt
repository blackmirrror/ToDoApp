package ru.blackmirrror.todoapp.presentation.todoItems

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import ru.blackmirrror.todoapp.data.Task

class TodoItemCallback: ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
        return oldItem == newItem
    }
}