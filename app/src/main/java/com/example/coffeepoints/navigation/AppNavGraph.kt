package com.example.coffeepoints.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    registrationScreenContent: @Composable () -> Unit,
    logInScreenContent: @Composable () -> Unit,
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
    }

}