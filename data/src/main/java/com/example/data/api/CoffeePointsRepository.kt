package com.example.data.api

import com.example.data.network.models.CoffeePointDTO

interface CoffeePointsRepository {

    suspend fun getCoffeePoints() : List<CoffeePointDTO>
}