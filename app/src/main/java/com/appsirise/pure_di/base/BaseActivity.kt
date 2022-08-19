package com.appsirise.pure_di.base

import androidx.appcompat.app.AppCompatActivity
import com.appsirise.pure_di.MyApplication
import com.appsirise.pure_di.di.ActivityDiRoot
import com.appsirise.pure_di.di.AppDiRoot

open class BaseActivity: AppCompatActivity() {

    private val applicationDiRoot: AppDiRoot get() = (application as MyApplication).appDiRoot

    val activityDiRoot: ActivityDiRoot by lazy { ActivityDiRoot(this, applicationDiRoot)}
}