package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.BottomNavigationBar
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(navController: NavController, viewModel: MoviesViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SimpleTopAppBar(title = "Watchlist")
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        (MovieList(
            viewModel,
            viewModel.movieList.filter { movie: Movie -> movie.isFavoriteMovie },
            padding,
            navController
        ))
    })
}