package com.example.data.api

import com.example.data.local.models.MapItemDbModel

interface MapPointsRepository {

    suspend fun getMapPoints() : List<MapItemDbModel>
}