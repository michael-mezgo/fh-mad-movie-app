package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movieList = MutableStateFlow(listOf<MovieWithImages>())

    init {

        val movies = getMovies()
        viewModelScope.launch {
            var i = 0
            movies.forEach { movie: Movie ->
                repository.add(movie)
                movie.images.forEach { image ->
                    repository.addMovieImage(movieImage = MovieImage(movieId = i.toLong(), url = image))
                }
                i++
            }
        }

        viewModelScope.launch {//coroutine scope - or name function suspend (func that runs for a long time)
            repository.getAllMovies().collect { movies ->
                _movieList.value = movies
            }
        }
    }

    val movieList: StateFlow<List<MovieWithImages>> = _movieList.asStateFlow()

    fun toggleIsFavorite(movie: Movie) {

        movie.isFavoriteMovie = !movie.isFavoriteMovie

        viewModelScope.launch {
            repository.update(movie)
        }
    }
}