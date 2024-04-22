package com.example.movieappmad24.data

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie

interface MovieDao {
    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("SELECT * FROM movie WHERE movie.dbId = :id")
    fun getById(id: Long) : Movie

    @Query("SELECT * FROM movie")
    fun getAllMovies() : List<Movie>

    @Query("SELECT * FROM movie WHERE isFavoriteMovie = 1")
    fun getFavoriteMovies() : List<Movie>
}