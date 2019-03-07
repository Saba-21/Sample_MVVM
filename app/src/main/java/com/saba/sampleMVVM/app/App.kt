package com.saba.sampleMVVM.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.saba.sampleMVVM.app.AppModule.appModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        startKoin(this, modules = listOf(appModule))
    }

}