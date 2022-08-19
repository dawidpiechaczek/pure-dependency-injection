package com.appsirise.pure_di.di

import android.app.Activity
import com.appsirise.pure_di.networking.CatsApi
import com.appsirise.pure_di.screens.breeddetails.FetchCatBreedDetailsUseCase
import com.appsirise.pure_di.screens.breedslist.FetchCatBreedsUseCase
import com.appsirise.pure_di.screens.common.ScreensNavigator

class ActivityDiRoot(private val activity: Activity, private val applicationDiRoot: AppDiRoot) {

    val screensNavigator by lazy { ScreensNavigator(activity) }

    private val catsApi: CatsApi get() = applicationDiRoot.catsApi

    val fetchCatBreedsUseCase get() = FetchCatBreedsUseCase(catsApi)

    val fetchCatBreedDetailsUseCase get() = FetchCatBreedDetailsUseCase(catsApi)
}