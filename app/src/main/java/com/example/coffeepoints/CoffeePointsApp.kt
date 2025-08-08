package com.example.coffeepoints

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.coffeepoints.di.ApplicationComponent
import com.example.coffeepoints.di.DaggerApplicationComponent
import com.yandex.mapkit.MapKitFactory

class CoffeePointsApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        super.onCreate()

        MapKitFactory.setApiKey(BuildConfig.API_KEY)
        MapKitFactory.initialize(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as CoffeePointsApp).component
}