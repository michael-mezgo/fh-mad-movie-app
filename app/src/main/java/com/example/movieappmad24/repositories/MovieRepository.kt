package com.example.movieappmad24.repositories

import com.example.movieappmad24.data.MovieDao
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage

class MovieRepository (private val movieDao: MovieDao) { //Principle of dependency injection (inject dependencies from the outside)
    suspend fun add(movie: Movie) {
        movieDao.insert(movie)
    }

    suspend fun addMovieImage(movieImage: MovieImage) = movieDao.addMovieImage(movieImage)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    fun getMovieById(id: Long?) = movieDao.getById(id)

    fun getAllMovies() = movieDao.getAllMovies()

    fun getFavoriteMovies() = movieDao.getFavoriteMovies()

    fun getAllMovieIds() = movieDao.getAllMovieIds()

    suspend fun countMovies() = movieDao.countMovies()
}