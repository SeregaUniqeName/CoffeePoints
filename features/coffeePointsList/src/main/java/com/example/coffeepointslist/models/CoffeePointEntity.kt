package com.example.coffeepointslist.models

data class CoffeePointEntity(
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val distance: Float,
)
