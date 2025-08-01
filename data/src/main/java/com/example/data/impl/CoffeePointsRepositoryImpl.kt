package com.example.data.impl

import com.example.data.TokenOutOfTimeException
import com.example.data.api.CoffeePointsRepository
import com.example.data.network.CoffeePointsApiService
import com.example.data.network.models.CoffeePointDTO
import jakarta.inject.Inject
import retrofit2.HttpException

class CoffeePointsRepositoryImpl @Inject constructor(
    private val apiService: CoffeePointsApiService,
) : CoffeePointsRepository {

    override suspend fun getCoffeePoints(): List<CoffeePointDTO> {
        return try {
            apiService.getCoffeePoints()
        } catch(e: HttpException) {
            when(e.code()) {
                401 -> throw TokenOutOfTimeException()
                else -> throw e
            }
        }
        catch (e: Exception) {
            throw e
        }
    }
}