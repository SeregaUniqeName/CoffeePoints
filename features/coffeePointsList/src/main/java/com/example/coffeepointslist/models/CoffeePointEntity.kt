package com.example.coffeepointslist.models

import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoffeePointEntity(
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
    val distance: Float,
) : Parcelable {

    companion object {
        val NavigationType: NavType<CoffeePointEntity> = object : NavType<CoffeePointEntity>(false) {
            override fun put(bundle: SavedState, key: String, value: CoffeePointEntity) {
                return bundle.putParcelable(key, value)
            }

            override fun get(bundle: SavedState, key: String): CoffeePointEntity? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): CoffeePointEntity {
                return Gson().fromJson(value, CoffeePointEntity::class.java)
            }

        }
    }
}