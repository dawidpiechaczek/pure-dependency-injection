package com.appsirise.pure_di.screens.breeddetails

import com.appsirise.pure_di.Constants
import com.appsirise.pure_di.networking.CatsApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchCatBreedDetailsUseCase {

    sealed class Result {
        data class Success(val description: String) : Result()
        object Failure : Result()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val stackoverflowApi: CatsApi = retrofit.create(CatsApi::class.java)

    suspend fun fetchCatBreedDetails(catId: String): Result {
        return withContext(Dispatchers.IO) {
            try {
                val response = stackoverflowApi.fetchBreedDetails(catId)
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