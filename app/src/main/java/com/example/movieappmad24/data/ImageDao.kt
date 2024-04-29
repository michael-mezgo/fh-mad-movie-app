package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Insert
import com.example.movieappmad24.models.MovieImage

@Dao
interface ImageDao {
    @Insert
    suspend fun insertImages(movieImages: List<MovieImage>)
}