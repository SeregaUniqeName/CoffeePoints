package com.example.data.local.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class MenuItemDbModel(
    @PrimaryKey val id: Int,
    val coffeePointId: Int,
    val name: String,
    val imageUrl: String,
    val price: Int,
    val count: Int = 0
)

@Entity
data class CoffeePointDbModel(
    @PrimaryKey val id: Int,
)

data class CoffeePointOrder(
    @Embedded val coffeePoint: CoffeePointDbModel,
    @Relation(
        parentColumn = "id",
        entityColumn = "coffeePointId",
    )
    val order: List<MenuItemDbModel>
)