package ru.blackmirrror.todoapp.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.blackmirrror.todoapp.presentation.auth.AuthViewModel
import ru.blackmirrror.todoapp.presentation.todoItems.TodoItemsViewModel

val appModule =  module {
    viewModel {
        AuthViewModel(
            authRepository = get()
        )
    }

    viewModel {
        TodoItemsViewModel(
            authRepository = get()
        )
    }
}