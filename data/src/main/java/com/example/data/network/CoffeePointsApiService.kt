package com.example.data.network

import com.example.data.network.models.CoffeePointDTO
import retrofit2.http.GET

interface CoffeePointsApiService {

    @GET("locations")
    suspend fun getCoffeePoints() : List<CoffeePointDTO>
}