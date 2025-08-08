package com.example.coffeepoints.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresPermission
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.coffeepoints.navigation.AppNavGraph
import com.example.coffeepoints.navigation.Screen
import com.example.coffeepoints.navigation.rememberNavigationState
import com.example.coffeepoints.ui.theme.CoffeePointsTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi


class MainActivity : ComponentActivity() {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                                onNextScreen = { navigationState.navigateToMenu(it) },
                                onTokenExpired = { navigationState.navigateOnTokenExpire(Screen.LogIn.route) },
                                onMapScreen = { navigationState.navigateTo(Screen.CoffeeShopMap.route) },
                            )
                        },
                        coffeePointMenuContent = {
                            CoffeePointMenuScreen(
                                paddingValues = paddingValues,
                                id = it
                            )
                        },
                        coffeeShopMapContent = {
                            CoffeeShopMapScreen(
                                paddingValues = paddingValues,
                                onNextScreen = { navigationState.navigateToMenu(it) }
                            )
                        }
                    )
                }
            }
        }
    }
}
