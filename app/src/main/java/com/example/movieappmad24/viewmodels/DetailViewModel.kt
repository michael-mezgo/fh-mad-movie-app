package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import com.example.movieappmad24.repositories.MovieRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    fun findMovieWithId(id: Long?): MovieWithImages? {
        var movieWithImages: MovieWithImages? = null
        viewModelScope.launch {
            movieWithImages = repository.getMovieById(id)
        }
        return movieWithImages
    }

    fun toggleIsFavorite(movie: Movie) {

        movie.isFavoriteMovie = !movie.isFavoriteMovie

        viewModelScope.launch {
            repository.update(movie)
        }
    }
}