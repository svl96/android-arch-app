package com.yandex.android.mynotesandroid

import android.app.Application
import com.yandex.android.mynotesandroid.di.AppComponent
import com.yandex.android.mynotesandroid.di.AppModule
import com.yandex.android.mynotesandroid.di.DaggerAppComponent


class App : Application() {

    private var mAppComponent : AppComponent? = null

    override fun onCreate() {
        super.onCreate()
        mAppComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun getAppComponent() : AppComponent {
        return mAppComponent!!
    }
}