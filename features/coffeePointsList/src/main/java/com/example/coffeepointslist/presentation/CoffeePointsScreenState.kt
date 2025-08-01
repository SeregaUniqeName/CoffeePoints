package com.example.coffeepointslist.presentation

import com.example.coffeepointslist.models.CoffeePointEntity

sealed class CoffeePointsScreenState {

    data object Loading : CoffeePointsScreenState()

    data class Loaded(
        val coffeePointsList: List<CoffeePointEntity>
    ) : CoffeePointsScreenState()

    data class Error(
        val message: String
    ) : CoffeePointsScreenState()

    data object TokenExpired : CoffeePointsScreenState()
}