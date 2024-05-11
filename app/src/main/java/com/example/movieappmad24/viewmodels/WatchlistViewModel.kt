package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchlistViewModel (private val repository: MovieRepository) : ViewModel() {
    private val _favoriteMovies = MutableStateFlow(listOf<MovieWithImages>())

    val favoriteMovies: StateFlow<List<MovieWithImages>> = _favoriteMovies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect{
                _favoriteMovies.value = it
            }
        }
    }

    fun toggleFavorite(movie: Movie) {
        movie.isFavoriteMovie = !movie.isFavoriteMovie

        viewModelScope.launch {
            repository.update(movie)
        }
    }
}