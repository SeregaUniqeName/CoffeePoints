package com.example.data.impl

import com.example.data.TokenOutOfTimeException
import com.example.data.api.CoffeePointsRepository
import com.example.data.local.database.MapItemsDao
import com.example.data.network.CoffeePointsApiService
import com.example.data.network.models.CoffeePointDTO
import com.example.data.utils.toDbModel
import jakarta.inject.Inject
import retrofit2.HttpException

class CoffeePointsRepositoryImpl @Inject constructor(
    private val apiService: CoffeePointsApiService,
    private val database: MapItemsDao,
) : CoffeePointsRepository {

    override suspend fun getCoffeePoints(): List<CoffeePointDTO> {
        try {
            val list = apiService.getCoffeePoints()
            database.addMapItems(list.map { it.toDbModel() })
            return list
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> throw TokenOutOfTimeException()
                else -> throw e
            }
        } catch (e: Exception) {
            throw e
        }
    }
}