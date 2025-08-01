package com.example.coffeepointslist.useCases

import com.example.coffeepointslist.models.CoffeePointEntity
import com.example.coffeepointslist.utils.mapToEntity
import com.example.data.api.CoffeePointsRepository
import jakarta.inject.Inject


class GetAllPointsUseCase @Inject constructor(
    private val repository: CoffeePointsRepository,
) {

    suspend operator fun invoke() : List<CoffeePointEntity> {
        return repository.getCoffeePoints().map { it.mapToEntity() }
    }
}