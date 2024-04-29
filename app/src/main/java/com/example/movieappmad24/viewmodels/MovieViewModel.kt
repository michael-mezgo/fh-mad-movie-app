package com.example.movieappmad24.viewmodels

import com.example.movieappmad24.models.Movie

interface MovieViewModel {
    fun toggleIsFavorite(movie: Movie)
}