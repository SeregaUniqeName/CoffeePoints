package com.example.data.utils

import com.example.data.local.models.MapItemDb
import com.example.data.network.models.CoffeePointDTO

fun CoffeePointDTO.toDbModel() : MapItemDb {
    return MapItemDb(
        id = this.id,
        name = this.name,
        lat = this.coordinates.latitude,
        lon = this.coordinates.longitude
    )
}

