package com.appsirise.pure_di

import android.app.Application
import com.appsirise.pure_di.di.AppDiRoot

class MyApplication : Application() {

    lateinit var appDiRoot: AppDiRoot

    override fun onCreate() {
        appDiRoot = AppDiRoot()
        super.onCreate()
    }
}