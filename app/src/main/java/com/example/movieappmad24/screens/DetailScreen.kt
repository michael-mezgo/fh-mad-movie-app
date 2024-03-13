package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.movieappmad24.models.getMovies

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(movieId: String, navController: NavController) {
    val movie = getMovies().find { it.id == movieId }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = {
                if (movie != null) {
                    Text(movie.title)
                } else Text("Detail page")
            })
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Text("abc")
            }
        }
    )
}