package com.example.movieappmad24.models

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithImages(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "dbId",
        entityColumn = "movieId"
    )
    val movieImages: List<MovieImage>
)