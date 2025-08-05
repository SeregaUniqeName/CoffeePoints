package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("token") val token: String,
    @SerializedName("tokenLifeTime") val tokenLifeTime: Int,
)

