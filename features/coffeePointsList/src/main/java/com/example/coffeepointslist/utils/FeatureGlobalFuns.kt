package com.example.coffeepointslist.utils

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt


fun calculateDistance(latUser: Double, lonUser: Double, latPoint: Double, lonPoint: Double) : Float {
    val R = 6371e3
    val lat1Rad = Math.toRadians(latUser)
    val lat2Rad = Math.toRadians(latPoint)
    val deltaLat = Math.toRadians(latPoint - latUser)
    val deltaLon = Math.toRadians(lonPoint - lonUser)

    val a = sin(deltaLat / 2) * sin(deltaLat / 2) +
            cos(lat1Rad) * cos(lat2Rad) *
            sin(deltaLon / 2) * sin(deltaLon / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = R * c
    return (distance/1000).toFloat()
}