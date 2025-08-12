package com.example.data.api

import com.example.data.local.models.MenuItemDbModel
import com.example.data.network.models.CoffeeMenuItemDTO

interface CoffeePointMenuRepository {

    suspend fun getMenu(id: Int) : List<MenuItemDbModel>
    suspend fun add(item: MenuItemDbModel) : List<MenuItemDbModel>
}