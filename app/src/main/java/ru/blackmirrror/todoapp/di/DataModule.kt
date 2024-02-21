package ru.blackmirrror.todoapp.di

import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module
import ru.blackmirrror.todoapp.data.repositories.AuthRepositoryImpl
import ru.blackmirrror.todoapp.data.repositories.AuthRepository

val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            auth = FirebaseAuth.getInstance()
        )
    }
}