package com.appsirise.pure_di.screens.breedslist

import com.appsirise.pure_di.breeds.CatBreed
import com.appsirise.pure_di.networking.CatsApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchCatBreedsUseCase(private val catsApi: CatsApi) {

    sealed class Result {
        data class Success(val cats: List<CatBreed>) : Result()
        object Failure : Result()
    }

    suspend fun fetchCatBreeds(): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.fetchCatBreeds(20)
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