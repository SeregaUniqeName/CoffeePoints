package com.example.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.local.models.CoffeePointDbModel
import com.example.data.local.models.MenuItemDbModel

@Database(entities = [MenuItemDbModel::class, CoffeePointDbModel::class], version = 1, exportSchema = false)
abstract class MenuItemsDatabase : RoomDatabase() {

    abstract fun menuItemsDao() : MenuItemsDao

    companion object {

        private var INSTANCE: MenuItemsDatabase? = null
        private val lock = Any()
        private const val DB_NAME = "order"

        fun getInstance(context: Context) : MenuItemsDatabase {
            INSTANCE?.let {
                return it
            }
            synchronized(lock) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    context,
                    MenuItemsDatabase::class.java,
                    DB_NAME
                ).build()
                INSTANCE = db
                return db
            }
        }
    }
}