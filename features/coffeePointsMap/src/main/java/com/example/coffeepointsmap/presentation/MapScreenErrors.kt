package com.example.coffeepointsmap.presentation

sealed interface MapScreenErrors {
    data class Common(val message: String) : MapScreenErrors
    data object Empty : MapScreenErrors
}

