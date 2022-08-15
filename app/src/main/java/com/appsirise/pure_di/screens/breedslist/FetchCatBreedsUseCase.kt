package com.appsirise.pure_di.screens.breedslist

import com.appsirise.pure_di.Constants
import com.appsirise.pure_di.breeds.CatBreed
import com.appsirise.pure_di.networking.CatsApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchCatBreedsUseCase {

    sealed class Result {
        data class Success(val cats: List<CatBreed>) : Result()
        object Failure : Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val stackoverflowApi: CatsApi = retrofit.create(CatsApi::class.java)

    suspend fun fetchCatBreeds(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.fetchCatBreeds(20)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!)
                } else {
                    return@withContext Result.Failure
                }
            } catch (error: Throwable) {
                if (error !is CancellationException) {
                    return@withContext Result.Failure
                } else {
                    throw error
                }
            }
        }
    }
}