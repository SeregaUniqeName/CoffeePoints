package com.example.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.models.MapItemDb

@Database(entities = [MapItemDb::class], version = 1, exportSchema = false)
abstract class MapItemsDatabase : RoomDatabase() {

    abstract fun mapItemsDao() : MapItemsDao

    companion object {

        private var INSTANCE: MapItemsDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "map_item.db"

        fun getInstance(context: Context) : MapItemsDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    MapItemsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}