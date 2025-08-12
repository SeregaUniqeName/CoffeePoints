package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class CoffeeMenuItemDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("imageURL") val imageUrl: String,
    @SerializedName("price") val price: Int
)
