package ru.blackmirrror.todoapp.presentation.todoItems

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.blackmirrror.todoapp.R
import ru.blackmirrror.todoapp.data.Importance
import ru.blackmirrror.todoapp.data.Task
import ru.blackmirrror.todoapp.data.utils.TextFormatter.fromLongToString

class TodoItemsAdapter: ListAdapter<Task, TodoItemsAdapter.TodoItemsViewHolder>(TodoItemCallback()) {

    var onTodoItemClickListener: ((Task) -> Unit)? = null
    var onTodoItemLongClickListener: ((Task) -> Unit)? = null

    class TodoItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val completed: CheckBox = itemView.findViewById(R.id.item_done)
        val text: TextView = itemView.findViewById(R.id.item_text)
        val info: ImageButton = itemView.findViewById(R.id.item_info_btn)
        val deadline: TextView = itemView.findViewById(R.id.item_deadline)
        val importance: ImageView = itemView.findViewById(R.id.iv_importance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoItemsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemsViewHolder, position: Int) {
        val todoItem = getItem(position)
        with(holder) {
            completed.isChecked = todoItem.completed
            text.text = todoItem.text
            if (todoItem.completed) {
                text.paintFlags = text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                importance.setImageResource(0)
                completed.buttonTintList = AppCompatResources.getColorStateList(
                    itemView.context,
                    R.color.color_green
                )
            }
            else {
                text.paintFlags = 0
                completed.buttonTintList = AppCompatResources.getColorStateList(
                    itemView.context,
                    R.color.color_gray
                )
                if (todoItem.importance == Importance.LOW)
                    importance.setImageResource(R.drawable.ic_importance_low)
                else if (todoItem.importance == Importance.HIGH) {
                    importance.setImageResource(R.drawable.ic_importance_high)
                    completed.buttonTintList = AppCompatResources.getColorStateList(
                        itemView.context,
                        R.color.color_red
                    )
                }
            }
            if (todoItem.deadlineDate != null) {
                deadline.visibility = View.VISIBLE
                deadline.text = fromLongToString(todoItem.deadlineDate!!)
            }
            else
                deadline.visibility = View.GONE
            itemView.setOnClickListener {
                onTodoItemClickListener?.invoke(todoItem)
            }
            itemView.setOnLongClickListener {
                onTodoItemLongClickListener?.invoke(todoItem)
                true
            }
        }
    }
}