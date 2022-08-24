package com.appsirise.pure_di.di

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.appsirise.pure_di.networking.CatsApi
import com.appsirise.pure_di.screens.breeddetails.FetchCatBreedDetailsUseCase
import com.appsirise.pure_di.screens.breedslist.FetchCatBreedsUseCase
import com.appsirise.pure_di.screens.common.ScreensNavigator
import com.appsirise.pure_di.screens.common.dialogs.DialogsNavigator

class ActivityDiRoot(
    private val activity: AppCompatActivity,
    private val applicationDiRoot: AppDiRoot
) {

    val screensNavigator by lazy { ScreensNavigator(activity) }

    private val fragmentManager: FragmentManager get() = activity.supportFragmentManager

    val dialogsNavigator by lazy { DialogsNavigator(fragmentManager) }

    private val catsApi: CatsApi get() = applicationDiRoot.catsApi

    val fetchCatBreedsUseCase get() = FetchCatBreedsUseCase(catsApi)

    val fetchCatBreedDetailsUseCase get() = FetchCatBreedDetailsUseCase(catsApi)
}