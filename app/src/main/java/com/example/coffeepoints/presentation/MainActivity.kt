package com.example.coffeepoints.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.coffeepoints.navigation.AppNavGraph
import com.example.coffeepoints.navigation.NavigationState
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

                    AppNavGraph(
                        navHostController = navigationState.navHostController,
                        registrationScreenContent = {
                            TopAppBarCustom(
                                titleResId = Screen.Registration.titleId,
                                navigationState = navigationState,
                                hasBackButton = Screen.Registration.hasBackButton
                            ) {
                                RegistrationScreen(it) {
                                    navigationState.navigateTo(Screen.LogIn.route)
                                }
                            }

                        },
                        logInScreenContent = {
                            TopAppBarCustom(
                                titleResId = Screen.LogIn.titleId,
                                navigationState = navigationState,
                                hasBackButton = Screen.LogIn.hasBackButton
                            ) {
                                LogInScreen(it) {
                                    navigationState.navigateTo(Screen.CoffeeShopList.route)
                                }
                            }
                        },
                        coffeeShopListContent = {
                            TopAppBarCustom(
                                titleResId = Screen.CoffeeShopList.titleId,
                                navigationState = navigationState,
                                hasBackButton = Screen.CoffeeShopList.hasBackButton
                            ) { paddingValues ->
                                CoffeePointsScreen(
                                    paddingValues = paddingValues,
                                    onNextScreen = { navigationState.navigateToMenu(it) },
                                    onTokenExpired = { navigationState.navigateOnTokenExpire(Screen.LogIn.route) },
                                    onMapScreen = { navigationState.navigateTo(Screen.CoffeeShopMap.route) },
                                )
                            }

                        },
                        coffeePointMenuContent = { id ->
                            TopAppBarCustom(
                                titleResId = Screen.CoffeePointMenu.titleId,
                                navigationState = navigationState,
                                hasBackButton = Screen.CoffeePointMenu.hasBackButton
                            ) {
                                CoffeePointMenuScreen(
                                    paddingValues = it,
                                    id = id,
                                    onNextScreen = { navigationState.navigateToConfirm(id) },
                                    onTokenExpired = { navigationState.navigateOnTokenExpire(Screen.LogIn.route) },
                                )
                            }

                        },
                        coffeeShopMapContent = {
                            TopAppBarCustom(
                                titleResId = Screen.CoffeeShopMap.titleId,
                                navigationState = navigationState,
                                hasBackButton = Screen.CoffeeShopMap.hasBackButton
                            ) { paddingValues ->
                                CoffeeShopMapScreen(
                                    paddingValues = paddingValues,
                                    onNextScreen = { navigationState.navigateToMenu(it) }
                                )
                            }
                        },
                        confirmScreenContent = { id ->
                            TopAppBarCustom(
                                titleResId = Screen.CoffeePointConfirm.titleId,
                                navigationState = navigationState,
                                hasBackButton = Screen.CoffeePointConfirm.hasBackButton
                            ) {
                                ConfirmOrderScreen(
                                    paddingValues = it,
                                    id = id,
                                )
                            }

                        }
                    )
                }
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarCustom(
    titleResId: Int,
    navigationState: NavigationState,
    hasBackButton: Boolean,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(modifier = Modifier, text = stringResource(titleResId))
                },
                navigationIcon = {
                    if (hasBackButton) {
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
        content(paddingValues)
    }
}

