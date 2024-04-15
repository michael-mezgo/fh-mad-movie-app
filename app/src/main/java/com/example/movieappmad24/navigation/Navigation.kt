package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.MoviesViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val viewModel: MoviesViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {

        composable(route = Screens.Home.route) {
            HomeScreen(navController, viewModel)
        }

        composable(route = Screens.Detail.route)
        { backStackEntry ->
            DetailScreen(
                movieId = backStackEntry.arguments?.getString(MOVIE_ID),
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route = Screens.Watchlist.route) {
            WatchlistScreen(navController, viewModel)
        }
    }
}