package com.example.acto_poc.di.module

import com.example.acto_poc.MainApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private var mainApp: MainApp) {

    @Singleton
    @Provides
    fun provideMainApp():MainApp{
        return mainApp;
    }
}