package com.appsirise.pure_di.networking

import com.appsirise.pure_di.breeds.BreedDetails
import com.appsirise.pure_di.breeds.CatBreed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CatsApi {
    @GET("/v1/breeds")
    suspend fun fetchCatBreeds(
        @Query("limit") catsLimit: Int?,
        @Query("page") page: Int = 0
    ): Response<List<CatBreed>>

    @GET("/v1/breeds/{breedId}")
    suspend fun fetchBreedDetails(@Path("breedId") breedId: String?): Response<BreedDetails>
}