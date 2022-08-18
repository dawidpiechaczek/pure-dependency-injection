package com.appsirise.pure_di.di

import com.appsirise.pure_di.Constants
import com.appsirise.pure_di.networking.CatsApi
import com.appsirise.pure_di.screens.breeddetails.FetchCatBreedDetailsUseCase
import com.appsirise.pure_di.screens.breedslist.FetchCatBreedsUseCase
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppDiRoot {

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val catsApi: CatsApi = retrofit.create(CatsApi::class.java)

     val fetchCatBreedsUseCase get()  = FetchCatBreedsUseCase(catsApi)

     val fetchCatBreedDetailsUseCase get()  = FetchCatBreedDetailsUseCase(catsApi)

}