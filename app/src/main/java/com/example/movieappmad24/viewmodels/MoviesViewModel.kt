package com.example.movieappmad24.viewmodels

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies

class MoviesViewModel : ViewModel() {
    private val _movieList = getMovies().toMutableStateList() // get all movies and create a StateHolder from it, so it can be observed by UI
    val movieList: List<Movie> // expose previously created list but immutable
        get() = _movieList

    val favoriteMovieList: List<Movie> = getMovies().toMutableStateList()
    fun toggleIsFavorite(movie: Movie) {
        movie.isFavoriteMovie = !movie.isFavoriteMovie
    }
}