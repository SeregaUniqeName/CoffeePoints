package com.example.coffeepointsmenu.presentation

import com.example.coffeepointsmenu.models.CoffeeMenuItem

sealed class CoffeeMenuScreenState {

    data object Loading : CoffeeMenuScreenState()
    data class Content(
        val list: List<CoffeeMenuItem>,
        val error: Error
    ) : CoffeeMenuScreenState() {
        sealed interface Error {
            data object Empty: Error
            data class Common(
                val message: String
            ) : Error
        }
    }
    data object TokenExpired : CoffeeMenuScreenState()
}