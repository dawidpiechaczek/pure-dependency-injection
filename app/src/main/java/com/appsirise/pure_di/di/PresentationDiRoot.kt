package com.appsirise.pure_di.di

import com.appsirise.pure_di.screens.breeddetails.FetchCatBreedDetailsUseCase
import com.appsirise.pure_di.screens.breedslist.FetchCatBreedsUseCase
import com.appsirise.pure_di.screens.common.ViewFactory
import com.appsirise.pure_di.screens.common.dialogs.DialogsNavigator

class PresentationDiRoot(private val activityDiRoot: ActivityDiRoot) {

    private val layoutInflater get() = activityDiRoot.layoutInflater

    private val fragmentManager get() = activityDiRoot.fragmentManager

    private val catsApi get() = activityDiRoot.catsApi

    val viewFactory get() = ViewFactory(layoutInflater)

    val screensNavigator get() = activityDiRoot.screensNavigator

    val dialogsNavigator by lazy { DialogsNavigator(fragmentManager) }

    val fetchCatBreedsUseCase get() = FetchCatBreedsUseCase(catsApi)

    val fetchCatBreedDetailsUseCase get() = FetchCatBreedDetailsUseCase(catsApi)
}