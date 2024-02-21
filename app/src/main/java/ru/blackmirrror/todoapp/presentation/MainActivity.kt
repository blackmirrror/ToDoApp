package ru.blackmirrror.todoapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.blackmirrror.todoapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}