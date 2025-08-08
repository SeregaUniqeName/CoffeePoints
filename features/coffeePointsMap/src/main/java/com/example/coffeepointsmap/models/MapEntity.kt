package com.example.coffeepointsmap.models

import android.os.Parcelable
import androidx.navigation.NavType
import androidx.savedstate.SavedState
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
data class MapEntity(
    val id: Int,
    val name: String,
    val lat: Double,
    val lon: Double,
) : Parcelable {

    companion object {
        val NavigationType: NavType<MapEntity> = object : NavType<MapEntity>(false) {
            override fun put(bundle: SavedState, key: String, value: MapEntity) {
                return bundle.putParcelable(key, value)
            }

            override fun get(bundle: SavedState, key: String): MapEntity? {
                return bundle.getParcelable(key)
            }

            override fun parseValue(value: String): MapEntity {
                return Gson().fromJson(value, MapEntity::class.java)
            }

        }
    }
}
