package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage

@Database (
    entities = [Movie::class, MovieImage::class],
    version = 3,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile //this instance will never be cached
        private var Instance: MovieDatabase? = null

        fun getDatabase(context: Context) : MovieDatabase {
            return Instance?: synchronized(this) { //if instance not null return instance -> otherwise create instance with Builder; synchronized helps us with multithreading (no other thread can access this block)
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration() //not good for production Database, delete Data at migration
                    .build()
                    .also { Instance = it } //write into instance variable
            }
        }
    }
}