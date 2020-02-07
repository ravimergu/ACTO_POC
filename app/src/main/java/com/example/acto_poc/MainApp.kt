package com.example.acto_poc

import android.app.Application
import com.example.acto_poc.di.AppComponent
import com.example.acto_poc.di.module.AppModule
import com.example.acto_poc.di.DaggerAppComponent

class MainApp : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().appModule(
            AppModule(
                this
            )
        )
            .build()
    }
}