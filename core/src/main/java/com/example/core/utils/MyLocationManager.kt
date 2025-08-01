package com.example.core.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow

class MyLocationManager(
    private val context: Context,
    private val activity: Activity,
) {

    private val locationClient = LocationServices.getFusedLocationProviderClient(activity)

    private val _lat = MutableStateFlow<Double>(0.0)
    val lat get() = _lat
    private val _lon = MutableStateFlow<Double>(0.0)
    val lon get() = _lon

    @RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST
            )
            return
        }

        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lat.value = location.latitude
                lon.value =  location.longitude
            } else {
                Toast.makeText(context, "Unable to get coordinates!", Toast.LENGTH_LONG).show()
            }
        }
    }


    companion object {
        const val LOCATION_PERMISSION_REQUEST = 1001
    }
}