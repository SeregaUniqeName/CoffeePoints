package com.example.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "map_items")
data class MapItemDbModel(
    @PrimaryKey val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
)
