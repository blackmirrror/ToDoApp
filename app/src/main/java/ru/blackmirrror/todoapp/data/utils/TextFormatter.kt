package ru.blackmirrror.todoapp.data.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TextFormatter {

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
        return emailRegex.matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }

    fun dateToLong(date: Date): Long {
        return date.time
    }

    fun longToDate(timestamp: Long): Date {
        return Date(timestamp)
    }

    fun fromLongToString(date: Long): String {
        val sdf = SimpleDateFormat("d MMMM yyyy г.", Locale("ru"))
        return sdf.format(Date(date))
    }

}