package com.example.confermscreen.utils

import com.example.confermscreen.models.OrderItemModel
import com.example.data.local.models.MenuItemDbModel

internal fun MenuItemDbModel.toEntity() : OrderItemModel {
    return OrderItemModel(
        id = this.id,
        name = this.name,
        price = this.price,
        count = this.count
    )
}