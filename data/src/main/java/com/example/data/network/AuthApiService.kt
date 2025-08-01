package com.example.data.network

import com.example.data.network.models.TokenDTO
import com.example.data.network.models.UserPost
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun authorize(
        @Body user: UserPost
    ) : TokenDTO

    @POST("auth/register")
    suspend fun register(
        @Body user: UserPost
    ) : TokenDTO
}