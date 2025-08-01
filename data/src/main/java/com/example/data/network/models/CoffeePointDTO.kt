package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class CoffeePointDTO(
    @SerializedName("id")val id: Int,
    @SerializedName("name")val name: String,
    @SerializedName("point")val coordinates: CoordinatesDTO
) {
}

data class CoordinatesDTO(
    @SerializedName("latitude")val latitude: Double,
    @SerializedName("latitude")val longitude: Double,
)