package com.example.data.impl

import com.example.data.api.CoffeePointMenuRepository
import com.example.data.local.database.MenuItemsDao
import com.example.data.local.models.CoffeePointDbModel
import com.example.data.local.models.MenuItemDbModel
import com.example.data.network.CoffeePointsApiService
import com.example.data.utils.toDbModel
import jakarta.inject.Inject
import kotlinx.coroutines.CancellationException

class CoffeePointMenuRepositoryImpl @Inject constructor(
    private val database: MenuItemsDao,
    private val apiService: CoffeePointsApiService,
) : CoffeePointMenuRepository {

    override suspend fun getMenu(id: Int) : List<MenuItemDbModel> {
        return try {
            val coffeePoint = CoffeePointDbModel(id)
            database.addCoffeePoint(coffeePoint)
            val menu = apiService.getOrder(id).map { it.toDbModel(id) }
            menu
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun add(item: MenuItemDbModel) : List<MenuItemDbModel> {
        database.addItem(item)
        return database.getMenuItems(item.coffeePointId).order
    }

}