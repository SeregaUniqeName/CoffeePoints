package com.example.coffeepointsmap.utils

import com.example.coffeepointsmap.models.MapEntity
import com.example.data.local.models.MapItemDb

fun MapItemDb.toEntity() : MapEntity {
    return MapEntity(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon
    )
}