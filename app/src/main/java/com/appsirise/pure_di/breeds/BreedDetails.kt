package com.appsirise.pure_di.breeds

import com.google.gson.annotations.SerializedName

data class BreedDetails(
    @SerializedName("name") val name: String,
    @SerializedName("origin") val origin: String,
    @SerializedName("description") val description: String,
    @SerializedName("life_span") val lifeSpan: String
)