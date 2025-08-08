package com.example.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.models.MapItemDb

@Dao
interface MapItemsDao {

    @Query("SELECT * FROM map_items")
    fun getMapItems() : List<MapItemDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMapItems(list: List<MapItemDb>)
}