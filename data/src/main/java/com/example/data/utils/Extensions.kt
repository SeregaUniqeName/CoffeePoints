package com.example.data.utils

import com.example.data.local.models.MapItemDbModel
import com.example.data.local.models.MenuItemDbModel
import com.example.data.network.models.CoffeeMenuItemDTO
import com.example.data.network.models.CoffeePointDTO

fun CoffeePointDTO.toDbModel() : MapItemDbModel {
    return MapItemDbModel(
        id = this.id,
        name = this.name,
        lat = this.coordinates.latitude,
        lon = this.coordinates.longitude
    )
}

fun CoffeeMenuItemDTO.toDbModel(id: Int) : MenuItemDbModel {
    return MenuItemDbModel(
        id = this.id,
        coffeePointId = id,
        name = this.name,
        imageUrl = this.imageUrl,
        price = this.price
    )
}

