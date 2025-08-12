package com.example.data.network

import com.example.data.network.models.CoffeeMenuItemDTO
import com.example.data.network.models.CoffeePointDTO
import retrofit2.http.GET
import retrofit2.http.Path

interface CoffeePointsApiService {

    @GET("locations")
    suspend fun getCoffeePoints() : List<CoffeePointDTO>

    @GET("location/{id}/menu")
    suspend fun getOrder(@Path("id") id: Int) : List<CoffeeMenuItemDTO>
}