package com.ahorr.tesmotest

import android.app.Application
import com.ahorr.tesmotest.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TesMoTestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@TesMoTestApp)
            modules(appModule)
        }
    }
} 