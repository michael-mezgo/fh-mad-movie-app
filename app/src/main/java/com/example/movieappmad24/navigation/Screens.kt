package com.example.movieappmad24.navigation

const val MOVIE_ID = "MOVIE"

sealed class Screens(var route: String) {
    data object Home : Screens(route = "homescreen")
    object Detail : Screens(route = "detailscreen/{${MOVIE_ID}}") {
        fun setMovieId(movieId: String): String {
            return this.route.replace(oldValue = "{$MOVIE_ID}", newValue = movieId)
        }
    }

    data object Watchlist : Screens(route = "watchlistscreen")
}