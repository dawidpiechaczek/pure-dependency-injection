package com.appsirise.pure_di.di

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.appsirise.pure_di.networking.CatsApi
import com.appsirise.pure_di.screens.common.ScreensNavigator

class ActivityDiRoot(
    private val activity: AppCompatActivity,
    private val applicationDiRoot: AppDiRoot
) {
    val screensNavigator by lazy { ScreensNavigator(activity) }

    val layoutInflater get() = LayoutInflater.from(activity)

    val fragmentManager: FragmentManager get() = activity.supportFragmentManager

    val catsApi: CatsApi get() = applicationDiRoot.catsApi
}