package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.widgets.BottomNavigationBar
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(navController: NavController) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SimpleTopAppBar(title = "Watchlist")
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        (MovieList(movies = getMovies().subList(1, 3), padding, navController))
    })
}