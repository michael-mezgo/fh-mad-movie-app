package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController,
        startDestination = "homescreen") {

        composable(route = "homescreen") {
            HomeScreen()
        }

        composable(route = "detailscreen") {
            DetailScreen()
        }
    }
}