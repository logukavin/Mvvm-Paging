package com.example.mvvm.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class AppController : Application() {

    companion object {
        var instance: AppController? = null

        @JvmName("getInstance1")
        @Synchronized
        fun getInstance(): AppController? {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

}