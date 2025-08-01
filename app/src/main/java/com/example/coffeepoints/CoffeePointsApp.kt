package com.example.coffeepoints

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.coffeepoints.di.ApplicationComponent
import com.example.coffeepoints.di.DaggerApplicationComponent

class CoffeePointsApp : Application() {

    val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}

@Composable
fun getApplicationComponent(): ApplicationComponent {
    return (LocalContext.current.applicationContext as CoffeePointsApp).component
}