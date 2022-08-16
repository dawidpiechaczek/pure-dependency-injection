package com.appsirise.pure_di.screens.common

import android.app.Activity
import com.appsirise.pure_di.screens.breeddetails.CatBreedDetailsActivity

class ScreensNavigator(private val activity: Activity) {

    fun navigateToCatBreedDetails(breedId: String) {
        CatBreedDetailsActivity.start(activity, breedId)
    }

    fun navigateBack() {
        activity.onBackPressed()
    }
}