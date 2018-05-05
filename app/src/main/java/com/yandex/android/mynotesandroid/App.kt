package com.yandex.android.mynotesandroid

import android.app.Application
import com.yandex.android.mynotesandroid.di.AppComponent


class App : Application() {

    private var mAppComponent : AppComponent? = null

    override fun onCreate() {
        super.onCreate()
    }

    fun getAppComponent() : AppComponent {
        return mAppComponent!!
    }
}