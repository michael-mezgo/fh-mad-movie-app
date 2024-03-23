package com.example.movieappmad24.navigation

const val MOVIE_ID = "MOVIE"

sealed class Screen(var route: String) {
    data object Home : Screen(route = "homescreen")
    object Detail : Screen(route = "detailscreen/{${MOVIE_ID}}") {
        fun setMovieId(movieId: String): String {
            return this.route.replace(oldValue = "{$MOVIE_ID}", newValue = movieId)
        }
    }

    data object Watchlist : Screen(route = "watchlistscreen")
}