package com.example.coffeepointsmenu.useCases

import com.example.coffeepointsmenu.models.CoffeeMenuItem
import com.example.coffeepointsmenu.utils.toEntity
import com.example.data.api.CoffeePointMenuRepository
import jakarta.inject.Inject

class GetMenuItemsUseCase @Inject constructor(
    private val repository: CoffeePointMenuRepository,
) {

    suspend fun loadItems(id: Int) : List<CoffeeMenuItem>{
        return repository.getMenu(id).map { it.toEntity() }
    }

}