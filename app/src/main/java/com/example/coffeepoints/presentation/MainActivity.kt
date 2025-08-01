package com.example.coffeepoints.presentation

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.coffeepoints.navigation.AppNavGraph
import com.example.coffeepoints.navigation.Screen
import com.example.coffeepoints.navigation.rememberNavigationState
import com.example.coffeepoints.ui.theme.CoffeePointsTheme
import com.example.core.utils.MyLocationManager

class MainActivity : ComponentActivity() {

    private lateinit var locationManager: MyLocationManager

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationManager = MyLocationManager(applicationContext, this)

        setContent {
            CoffeePointsTheme {
                val navigationState = rememberNavigationState()
                Scaffold(
                    topBar = {
                        val navStackEntry by navigationState.navHostController.currentBackStackEntryAsState()

                        TopAppBar(
                            title = {
                                navStackEntry?.destination?.route?.let { Text(text = it) }
                            },
                            navigationIcon = {
                                if (navStackEntry?.destination?.route != Screen.NAV_LOGIN && navStackEntry?.destination?.route != Screen.NAV_REGISTRATION) {
                                    IconButton(onClick = { navigationState.navHostController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    AppNavGraph(
                        navHostController = navigationState.navHostController,
                        registrationScreenContent = {
                            RegistrationScreen(paddingValues) {
                                navigationState.navigateTo(Screen.LogIn.route)
                            }
                        },
                        logInScreenContent = {
                            LogInScreen(paddingValues) {
                                navigationState.navigateTo(Screen.CoffeeShopList.route)
                            }
                        },
                        coffeeShopListContent = {
                            CoffeePointsScreen(
                                paddingValues = paddingValues,
                                onNextScreen = { navigationState.navigateTo(Screen.CoffeeShopMap.route) },
                                onTokenExpired = { navigationState.navigateStackCleared(Screen.LogIn.route) },
                                locationManager = locationManager,
                                onMapScreen = {  },
                            )
                        },
                        coffeePointMenuContent = { CoffeePointMenuScreen(
                            paddingValues = paddingValues,
                            item = it
                        ) },
                        coffeeShopMapContent = { }
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)

        if (requestCode == MyLocationManager.Companion.LOCATION_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            locationManager.getCurrentLocation()
        } else {
            Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show()
        }
    }

}
