package ru.blackmirrror.todoapp.data.repositories

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import ru.blackmirrror.todoapp.data.Task

/**
 * @brief реализация репозитория задач
 * */

class TaskRepositoryImpl(
    private val auth: FirebaseAuth,
    private val dbReference: DatabaseReference
) : TaskRepository {

    private val userId = getCurrentUserId()

    override fun getAllTask(liveData: MutableLiveData<List<Task>?>) {
        if (userId != null) {
            dbReference.child(PATH_USERS).child(userId).child(PATH_TASKS)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val tasks = dataSnapshot.children.mapNotNull { it.getValue(Task::class.java) }
                        liveData.postValue(tasks)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        liveData.postValue(null)
                    }
                })
        }
    }

    override fun getNotCompletedTask(liveData: MutableLiveData<List<Task>?>) {
        if (userId != null) {
            dbReference.child(PATH_USERS).child(userId).child(PATH_TASKS)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val tasks = dataSnapshot.children
                            .mapNotNull { it.getValue(Task::class.java) }
                            .filter { !it.completed }
                        liveData.postValue(tasks)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        liveData.postValue(null)
                    }
                })
        }
    }


    override fun getTask(id: String, liveData: MutableLiveData<Task?>) {
        if (userId != null) {
            dbReference.child(PATH_USERS).child(userId).child(PATH_TASKS).child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val task: Task? = dataSnapshot.getValue(Task::class.java)
                        liveData.postValue(task)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        liveData.postValue(null)
                    }
                })
        }
    }

    override fun addTask(task: Task): Boolean {
        if (userId != null) {
            val taskId = dbReference.child(PATH_USERS).child(userId).child(PATH_TASKS).push().key
            if (taskId != null) {
                task.id = taskId
                dbReference.child(PATH_USERS).child(userId).child(PATH_TASKS).child(taskId)
                    .setValue(task)
                return true
            }
        }
        return false
    }

    override fun changeStatus(taskId: String, completed: Boolean) {
        userId?.let {
            dbReference.child(PATH_USERS).child(it).child(PATH_TASKS).child(taskId).child(
                VAL_COMPLETED
            ).setValue(completed)
        }
    }


    override fun updateTask(task: Task) {
        val taskId = task.id
        userId?.let {
            dbReference.child(PATH_USERS).child(it).child(PATH_TASKS).child(taskId)
                .setValue(task)
        }
    }

    override fun deleteTask(id: String) {
        userId?.let {
            dbReference.child(PATH_USERS).child(it).child(PATH_TASKS).child(id)
                .removeValue()
        }
    }

    private fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    companion object {
        private const val PATH_USERS = "users"
        private const val PATH_TASKS = "tasks"
        private const val VAL_COMPLETED = "completed"
    }
}