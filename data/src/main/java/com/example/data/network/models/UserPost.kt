package com.example.data.network.models

import com.google.gson.annotations.SerializedName

data class UserPost(
    @SerializedName("login") val login: String,
    @SerializedName("password") val password: String,
)
