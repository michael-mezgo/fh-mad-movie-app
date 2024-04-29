package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.repositories.MovieRepository
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {

        composable(route = Screens.Home.route) {
            HomeScreen(navController)
        }

        composable(route = Screens.Detail.route)
        { backStackEntry ->
            DetailScreen(
                movieId = backStackEntry.arguments?.getString(MOVIE_ID)?.toLong(),
                navController = navController
            )
        }

        composable(route = Screens.Watchlist.route) {
            WatchlistScreen(navController)
        }
    }
}