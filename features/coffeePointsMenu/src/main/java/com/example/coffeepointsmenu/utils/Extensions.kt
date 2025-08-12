package com.example.coffeepointsmenu.utils

import com.example.coffeepointsmenu.models.CoffeeMenuItem
import com.example.data.local.models.MenuItemDbModel

fun MenuItemDbModel.toEntity() : CoffeeMenuItem {
    return CoffeeMenuItem(
        id = this.id,
        coffeePointId = this.coffeePointId,
        name = this.name,
        imageUrl = this.imageUrl,
        price = this.price,
        count = this.count
    )
}

fun CoffeeMenuItem.toDbModel() : MenuItemDbModel {
    return MenuItemDbModel(
        id = this.id,
        coffeePointId = this.coffeePointId,
        name = this.name,
        imageUrl = this.imageUrl,
        price = this.price,
        count = this.count
    )
}