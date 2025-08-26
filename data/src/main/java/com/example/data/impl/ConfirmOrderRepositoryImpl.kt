package com.example.data.impl

import com.example.data.api.ConfirmOrderRepository
import com.example.data.local.database.MenuItemsDao
import com.example.data.local.models.MenuItemDbModel
import jakarta.inject.Inject

class ConfirmOrderRepositoryImpl @Inject constructor(
    private val database: MenuItemsDao,
) : ConfirmOrderRepository {
    override suspend fun getOrder(id: Int): List<MenuItemDbModel> {
        return database.getMenuItems(id).order
    }
}