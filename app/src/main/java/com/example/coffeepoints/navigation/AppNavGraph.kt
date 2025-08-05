package com.example.coffeepoints.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.coffeepointslist.models.CoffeePointEntity

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    registrationScreenContent: @Composable () -> Unit,
    logInScreenContent: @Composable () -> Unit,
    coffeeShopListContent: @Composable () -> Unit,
    coffeeShopMapContent: @Composable () -> Unit,
    coffeePointMenuContent: @Composable (CoffeePointEntity) -> Unit,
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
                    type = CoffeePointEntity.NavigationType
                }
            )
        ) {
            val feedPost = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.arguments?.getParcelable(Screen.KEY_COFFEE_POINT, CoffeePointEntity::class.java)
                    ?: throw RuntimeException("Args is null")
            } else {
                it.arguments?.getParcelable(Screen.KEY_COFFEE_POINT)
                    ?: throw RuntimeException("Args is null")
            }
            coffeePointMenuContent(feedPost)
        }
    }

}

