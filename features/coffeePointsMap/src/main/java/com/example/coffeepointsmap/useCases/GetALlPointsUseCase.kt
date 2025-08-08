package com.example.coffeepointsmap.useCases

import com.example.coffeepointsmap.models.MapEntity
import com.example.coffeepointsmap.utils.toEntity
import com.example.data.api.MapPointsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GetALlPointsUseCase @Inject constructor(
    private val repository: MapPointsRepository,
) {

    suspend operator fun invoke() : List<MapEntity> {
        return repository.getMapPoints().map { it.toEntity() }
    }
}