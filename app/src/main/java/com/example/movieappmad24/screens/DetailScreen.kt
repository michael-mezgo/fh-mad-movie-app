package com.example.movieappmad24.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun DetailScreen(movieId : String, navController: NavController) {
    Text(text = "Hello detail-screen $movieId")
}