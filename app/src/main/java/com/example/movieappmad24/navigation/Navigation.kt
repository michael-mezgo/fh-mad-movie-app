package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }

        composable(route = Screen.Detail.route)
        { backStackEntry ->
            DetailScreen(
                movieId = backStackEntry.arguments?.getString(MOVIE_ID),
                navController = navController
            )
        }

        composable(route = Screen.Watchlist.route) {
            WatchlistScreen(navController)
        }
    }
}