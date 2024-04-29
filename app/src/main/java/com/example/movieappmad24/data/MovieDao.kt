package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Delete
    suspend fun delete(movie: Movie)

    @Transaction
    @Query("SELECT * FROM movies WHERE movies.dbId = :id")
    suspend fun getById(id: Long?) : MovieWithImages

    @Transaction
    @Query("SELECT * FROM movies")
    fun getAllMovies() : Flow<List<MovieWithImages>>

    @Transaction
    @Query("SELECT * FROM movies WHERE isFavoriteMovie = 1")
    fun getFavoriteMovies() : Flow<List<MovieWithImages>>
}