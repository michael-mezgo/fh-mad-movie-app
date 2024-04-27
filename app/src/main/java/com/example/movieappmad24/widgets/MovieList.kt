package com.example.movieappmad24.widgets

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.navigation.Screens
import com.example.movieappmad24.viewmodels.MoviesViewModel
import kotlinx.coroutines.launch

@Composable
fun MovieList(
    moviesViewModel: MoviesViewModel, movieList: List<Movie>, padding: PaddingValues, navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(movieList) { movie ->
            MovieCard(movie, onFavoriteClick = {
                coroutineScope.launch {
                    moviesViewModel.toggleIsFavorite(movie)
                }
            }) { movieId ->
                val route = Screens.Detail.setMovieId(movieId)
                navController.navigate(route = route)
            }
        }
    }
}