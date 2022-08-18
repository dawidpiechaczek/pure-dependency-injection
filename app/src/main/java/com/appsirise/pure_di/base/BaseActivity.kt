package com.appsirise.pure_di.base

import androidx.appcompat.app.AppCompatActivity
import com.appsirise.pure_di.MyApplication
import com.appsirise.pure_di.di.AppDiRoot

open class BaseActivity: AppCompatActivity() {

    val appDiRoot: AppDiRoot get() = (application as MyApplication).appDiRoot
}