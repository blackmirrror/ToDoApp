package ru.blackmirrror.todoapp.data

import java.util.Date

data class Task(
    var id: String = "",
    var text: String,
    var importance: Importance = Importance.NONE,
    val completed: Boolean = false,
    var deadlineDate: Long? = null,
    val lastChanged: Long
) {
    constructor() : this("", "", Importance.NONE, false, null, Date().time)
}

enum class Importance {
    HIGH,
    LOW,
    NONE
}