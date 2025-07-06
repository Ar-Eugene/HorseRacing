package com.example.horseracing

import android.app.Application
import com.example.horseracing.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class HorseRacingApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        startKoin {
            androidLogger()
            androidContext(this@HorseRacingApplication)
            modules(appModule)
        }
    }
} 