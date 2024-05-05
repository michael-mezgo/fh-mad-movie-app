package com.example.movieappmad24.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie

@Composable
fun MovieCard(movie: Movie, onFavoriteClick: () -> Unit, onItemClick: (Long) -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clickable { onItemClick(movie.dbId) },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            MovieCardPictureAndFavorite(movie = movie, onFavoriteClick)

            MovieCardTitleAndDetails(movie = movie)
        }
    }
}

@Composable
fun MovieCardPictureAndFavorite(movie: Movie, onFavoriteClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // https://coil-kt.github.io/coil/compose/
        AsyncImage(
            model = if(movie.images.isNotEmpty()) movie.images[0] else null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.movie_image),
            contentDescription = "${movie.title} Image"
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onFavoriteClick() },
                tint =
                if(movie.isFavoriteMovie) Color.Red
                else Color.Gray,
                imageVector =
                if(movie.isFavoriteMovie) Icons.Default.Favorite
                else Icons.Default.FavoriteBorder,
                contentDescription = "Add to favorites"
            )
        }
    }
}

@Composable
fun MovieCardTitleAndDetails(movie: Movie) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = movie.title, fontWeight = FontWeight.Bold)
        Icon(
            modifier = Modifier
                .clickable {
                    showDetails = !showDetails
                },
            imageVector =
            if (showDetails) Icons.Filled.KeyboardArrowDown
            else Icons.Default.KeyboardArrowUp, contentDescription = "show more"
        )
    }

    AnimatedVisibility(visible = showDetails) {
        MovieDetails(movie = movie)
    }
}

@Composable
fun MovieDetails(movie: Movie) {
    Column(
        modifier = Modifier
            .padding(all = 15.dp)
    ) {
        Row {
            Text(
                """Director: ${movie.director}
                |Released: ${movie.year}
                |Genre: ${movie.genre}
                |Actors: ${movie.actors}
                |Rating: ${movie.rating}
            """.trimMargin()
            )
        }
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp)
        ) {
            HorizontalDivider()
        }
        Row {
            Text("Plot: ${movie.plot}")
        }
    }
}