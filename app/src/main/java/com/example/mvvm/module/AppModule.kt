package com.example.mvvm.module

import android.content.Context
import com.example.mvvm.application.AppController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    internal fun providesContext(): Context {
        return AppController.instance?.applicationContext!!
    }

}