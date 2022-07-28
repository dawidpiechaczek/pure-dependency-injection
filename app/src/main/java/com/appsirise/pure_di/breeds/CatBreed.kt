package com.appsirise.pure_di.breeds

import com.google.gson.annotations.SerializedName

data class CatBreed(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
)