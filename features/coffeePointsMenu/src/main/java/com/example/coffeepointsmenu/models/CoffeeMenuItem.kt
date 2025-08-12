package com.example.coffeepointsmenu.models

data class CoffeeMenuItem(
    val id: Int,
    val coffeePointId: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val count: Int
)
