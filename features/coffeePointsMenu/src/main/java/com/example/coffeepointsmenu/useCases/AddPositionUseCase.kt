package com.example.coffeepointsmenu.useCases

import com.example.coffeepointsmenu.models.CoffeeMenuItem
import com.example.coffeepointsmenu.utils.toDbModel
import com.example.coffeepointsmenu.utils.toEntity
import com.example.data.api.CoffeePointMenuRepository
import jakarta.inject.Inject

class AddPositionUseCase @Inject constructor(
    private val repository: CoffeePointMenuRepository,
) {

    suspend operator fun invoke(item: CoffeeMenuItem) : List<CoffeeMenuItem> {
        return repository.add(item.toDbModel()).map { it.toEntity() }
    }
}