package com.example.data.api

import com.example.data.local.models.MapItemDb

interface MapPointsRepository {

    suspend fun getMapPoints() : List<MapItemDb>
}