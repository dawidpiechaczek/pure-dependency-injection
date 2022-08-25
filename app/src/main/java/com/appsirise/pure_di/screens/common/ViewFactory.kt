package com.appsirise.pure_di.screens.common

import android.view.LayoutInflater
import android.view.ViewGroup
import com.appsirise.pure_di.screens.breeddetails.CatBreedDetailsView
import com.appsirise.pure_di.screens.breedslist.CatBreedsListView

class ViewFactory(private val layoutInflater: LayoutInflater) {

    fun newCatBreedsListView(parent: ViewGroup?): CatBreedsListView =
        CatBreedsListView(layoutInflater, parent)

    fun newCatBreedsDetailsView(): CatBreedDetailsView = CatBreedDetailsView(layoutInflater, null)
}