package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.repositories.MovieRepository
import com.example.movieappmad24.viewmodels.ViewModelFactory
import com.example.movieappmad24.viewmodels.WatchlistViewModel
import com.example.movieappmad24.widgets.BottomNavigationBar
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun WatchlistScreen(navController: NavController) {

    val db : MovieDatabase = MovieDatabase.getDatabase(LocalContext.current)
    val repository: MovieRepository = MovieRepository(movieDao = db.movieDao())
    val factory: ViewModelFactory = ViewModelFactory(repository = repository)
    val viewModel: WatchlistViewModel = viewModel(factory = factory)

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SimpleTopAppBar(title = "Watchlist")
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        (MovieList(
            viewModel.favoriteMovies,
            padding,
            navController,
            viewModel
        ))
    })
}