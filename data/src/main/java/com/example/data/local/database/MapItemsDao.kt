package com.example.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.models.MapItemDbModel

@Dao
interface MapItemsDao {

    @Query("SELECT * FROM map_items")
    fun getMapItems() : List<MapItemDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMapItems(list: List<MapItemDbModel>)
}