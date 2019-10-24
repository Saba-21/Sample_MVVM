package com.saba.sampleMVVM.app

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.saba.sampleMVVM.app.modules.AppModule
import com.saba.sampleMVVM.app.modules.UseCaseModule
import com.saba.sampleMVVM.app.modules.ViewModelModule
import org.koin.android.ext.android.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        startKoin(this, modules = listOf(AppModule.module, UseCaseModule.module, ViewModelModule.module))
    }

}