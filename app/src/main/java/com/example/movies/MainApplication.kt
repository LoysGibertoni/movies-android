package com.example.movies

import android.app.Application
import com.example.movies.data.DataModule
import com.example.movies.domain.DomainModule
import com.example.movies.presentation.PresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(DataModule, DomainModule, PresentationModule)
        }
    }
}