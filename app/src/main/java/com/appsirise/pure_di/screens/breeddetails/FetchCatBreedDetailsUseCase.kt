package com.appsirise.pure_di.screens.breeddetails

import com.appsirise.pure_di.networking.CatsApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FetchCatBreedDetailsUseCase(private val catsApi: CatsApi) {

    sealed class Result {
        data class Success(val description: String) : Result()
        object Failure : Result()
    }

    suspend fun fetchCatBreedDetails(catId: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = catsApi.fetchBreedDetails(catId)
                if (response.isSuccessful && response.body() != null) {
                    return@withContext Result.Success(response.body()!!.description)
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