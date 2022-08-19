package com.appsirise.pure_di.di

import com.appsirise.pure_di.Constants
import com.appsirise.pure_di.networking.CatsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppDiRoot {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

     val catsApi: CatsApi by lazy { retrofit.create(CatsApi::class.java) }

}