package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieList = MutableStateFlow(listOf<Movie>())

    init {
        viewModelScope.launch {
            getMovies().forEach { movie: Movie ->
                repository.add(movie)
            }
        }

        viewModelScope.launch {//coroutine scope - or name function suspend (func that runs for a long time)
            repository.getAllMovies().collect { movies ->
                _movieList.value = movies
            }
        }
    }

    val movieList: StateFlow<List<Movie>> = _movieList.asStateFlow()

    fun toggleIsFavorite(movie: Movie) {

        movie.isFavoriteMovie = !movie.isFavoriteMovie

        viewModelScope.launch {
            repository.update(movie)
        }
    }
}