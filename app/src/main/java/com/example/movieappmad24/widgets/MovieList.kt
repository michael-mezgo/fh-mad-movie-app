package com.example.movieappmad24.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.navigation.Screens
import com.example.movieappmad24.viewmodels.MovieViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun MovieList(
    movieList: StateFlow<List<MovieWithImages>>, padding: PaddingValues, navController: NavController, viewModel: MovieViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val moviesState by movieList.collectAsState()

    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(moviesState) { movieWithImages ->
            MovieCard(movieWithImages.movie, onFavoriteClick = {
                coroutineScope.launch {
                    viewModel.toggleIsFavorite(movieWithImages.movie)
                }
            }) { movieId ->
                val route = Screens.Detail.setMovieId(movieId)
                navController.navigate(route = route)
            }
        }
    }
}