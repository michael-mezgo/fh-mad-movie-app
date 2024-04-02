package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.BottomNavigationBar
import com.example.movieappmad24.widgets.MovieCard
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun HomeScreen(navController: NavController, viewModel: MoviesViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SimpleTopAppBar(title = "Movie App")
    }, bottomBar = {
        BottomNavigationBar(navController = navController)
    }, content = { padding ->
        (MovieList(moviesViewModel = viewModel, padding, navController))
    })
}

@Composable
fun MovieList(
    moviesViewModel: MoviesViewModel, padding: PaddingValues, navController: NavController
) {
    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(moviesViewModel.movieList) { movie ->
            MovieCard(movie, onFavoriteClick = {
                moviesViewModel.toggleIsFavorite(movie)
            }) { movieId ->
                val route = Screen.Detail.setMovieId(movieId)
                navController.navigate(route = route)
            }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MovieAppMAD24Theme {
        //MovieList(movies = getMovies(), padding = PaddingValues(10.dp), rememberNavController())
    }
}