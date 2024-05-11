package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.repositories.MovieRepository
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory
import com.example.movieappmad24.widgets.BottomNavigationBar
import com.example.movieappmad24.widgets.MovieList
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(navController: NavController) {
    val db : MovieDatabase = MovieDatabase.getDatabase(LocalContext.current)
    val repository: MovieRepository = MovieRepository(movieDao = db.movieDao())
    val factory: MoviesViewModelFactory = MoviesViewModelFactory(repository = repository)
    val viewModel: MoviesViewModel = viewModel(factory = factory)
    val movies by viewModel.movieList.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SimpleTopAppBar(title = "Movie App")
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        (MovieList(movies, padding, navController) {
            viewModel.toggleIsFavorite(it)
        })
    })
}

@Preview
@Composable
fun DefaultPreview() {
    MovieAppMAD24Theme {
        //MovieList(movies = getMovies(), padding = PaddingValues(10.dp), rememberNavController())
    }
}