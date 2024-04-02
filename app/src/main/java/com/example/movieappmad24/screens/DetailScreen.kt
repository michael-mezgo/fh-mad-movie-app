package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.CustomTopAppBar
import com.example.movieappmad24.widgets.MovieCard

@Composable
fun DetailScreen(movieId: String?, navController: NavController, viewModel: MoviesViewModel) {
    val movie = getMovies().find { it.id == movieId }

    if (movie == null) {
        Text("Movie not found")
        return
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomTopAppBar(title = movie.title, navController = navController)
        },

        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                Column {
                    Row {
                        MovieCard(movie = movie, onFavoriteClick = {})
                    }
                    LazyRow(
                        Modifier
                            .height(300.dp)
                    ) {
                        this.items(items = movie.images) { image ->
                            MoviePicture(resourceLink = image, title = movie.title)
                        }
                    }
                }
            }
        }
    )
}


@Composable
fun MoviePicture(resourceLink: String, title: String) {
    Card(
        shape = RoundedCornerShape(size = 20.dp),
        modifier = Modifier
            .padding(all = 15.dp)
    ) {
        AsyncImage(
            model = resourceLink,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.movie_image),
            contentDescription = "$title Image",
            modifier = Modifier
                .aspectRatio(ratio = 1f)
        )
    }
}