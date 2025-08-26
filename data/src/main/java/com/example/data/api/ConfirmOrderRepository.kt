package com.example.data.api

import com.example.data.local.models.MenuItemDbModel

interface ConfirmOrderRepository {

    suspend fun getOrder(id: Int) : List<MenuItemDbModel>
}