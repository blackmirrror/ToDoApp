package ru.blackmirrror.todoapp

import android.app.Application
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.blackmirrror.todoapp.di.appModule
import ru.blackmirrror.todoapp.di.dataModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        startKoin {
            androidContext(this@App)
            modules(dataModule, appModule)
        }
    }
}