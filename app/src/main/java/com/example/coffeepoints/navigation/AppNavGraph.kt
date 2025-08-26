package com.example.coffeepoints.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    registrationScreenContent: @Composable () -> Unit,
    logInScreenContent: @Composable () -> Unit,
    coffeeShopListContent: @Composable () -> Unit,
    coffeeShopMapContent: @Composable () -> Unit,
    coffeePointMenuContent: @Composable (Int) -> Unit,
    confirmScreenContent: @Composable (Int) -> Unit,
) {

    NavHost(
        navController = navHostController,
        startDestination = Screen.Registration.route,
    ) {
        composable(Screen.Registration.route) {
            registrationScreenContent()
        }
        composable(Screen.LogIn.route){
            logInScreenContent()
        }
        composable(Screen.CoffeeShopList.route) {
            coffeeShopListContent()
        }
        composable(Screen.CoffeeShopMap.route) {
            coffeeShopMapContent()
        }
        composable(
            route = Screen.CoffeePointMenu.route,
            arguments = listOf(
                navArgument(Screen.KEY_COFFEE_POINT) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(Screen.KEY_COFFEE_POINT)
                    ?: throw RuntimeException("Args is null")
            coffeePointMenuContent(id)
        }
        composable(
            route = Screen.NAV_COFFEE_POINT_CONFIRM,
            arguments = listOf(
                navArgument(Screen.KEY_COFFEE_POINT) {
                    type = NavType.IntType
                }
            )
        ) {
            val id = it.arguments?.getInt(Screen.KEY_COFFEE_POINT)
                ?: throw RuntimeException("Args is null")
            confirmScreenContent(id)
        }
    }

}

