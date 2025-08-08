package com.example.coffeepointslist.utils

import com.example.coffeepointslist.models.CoffeePointEntity
import com.example.data.network.models.CoffeePointDTO

fun CoffeePointDTO.mapToEntity() : CoffeePointEntity {

    return CoffeePointEntity(
        id = this.id,
        name = this.name,
        lat = this.coordinates.latitude,
        lon = this.coordinates.longitude,
        distance = -1.0F
    )
}
