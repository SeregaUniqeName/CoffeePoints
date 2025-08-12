package com.example.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.data.local.models.CoffeePointDbModel
import com.example.data.local.models.CoffeePointOrder
import com.example.data.local.models.MenuItemDbModel

@Dao
interface MenuItemsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCoffeePoint(coffeePoint: CoffeePointDbModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMenuItems(list: List<MenuItemDbModel>)

    @Transaction
    @Query("SELECT * FROM CoffeePointDbModel WHERE id=:id")
    fun getMenuItems(id: Int) : CoffeePointOrder

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addItem(item: MenuItemDbModel)
}