package com.example.data.impl

import com.example.data.api.MapPointsRepository
import com.example.data.local.database.MapItemsDao
import com.example.data.local.models.MapItemDbModel
import jakarta.inject.Inject

class MapPointsRepositoryImpl @Inject constructor(
    private val database: MapItemsDao
): MapPointsRepository {

    override suspend fun getMapPoints(): List<MapItemDbModel> {
        return database.getMapItems()
    }
}