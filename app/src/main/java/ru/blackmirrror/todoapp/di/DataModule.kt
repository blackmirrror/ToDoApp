package ru.blackmirrror.todoapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.dsl.module
import ru.blackmirrror.todoapp.data.repositories.AuthRepositoryImpl
import ru.blackmirrror.todoapp.data.repositories.AuthRepository
import ru.blackmirrror.todoapp.data.repositories.TaskRepository
import ru.blackmirrror.todoapp.data.repositories.TaskRepositoryImpl

val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = get()
        )
    }

    single<TaskRepository> {
        TaskRepositoryImpl(
            auth = get(),
            dbReference = get()
        )
    }

    single<DatabaseReference> {
        FirebaseDatabase.getInstance().reference
    }

    single<FirebaseAuth> {
        FirebaseAuth.getInstance()
    }
}