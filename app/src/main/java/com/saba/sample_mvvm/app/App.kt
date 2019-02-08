package com.saba.sample_mvvm.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.saba.sample_mvvm.app.AppModule.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        startKoin(this, modules = listOf(appModule))
    }

}