package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.widgets.CustomTopAppBar
import com.example.movieappmad24.widgets.MovieCard

@Composable
fun DetailScreen(movieId: String?, navController: NavController, viewModel: MoviesViewModel) {
    val movie = viewModel.movieList.find { it.id == movieId }

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
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    Row {
                        MovieCard(
                            movie = movie,
                            onFavoriteClick = { viewModel.toggleIsFavorite(movie) })
                    }
                    Row {
                        Text("Movie trailer")
                    }
                    Row {
                        TrailerPlayer(movie = movie)
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
fun TrailerPlayer(movie: Movie) {
    // Sources:
    // https://medium.com/@munbonecci/how-to-display-videos-using-exoplayer-on-android-with-jetpack-compose-1fb4d57778f4
    // https://developer.android.com/media/media3/exoplayer?hl=de
    val context = LocalContext.current

    val movieTrailer =
        MediaItem.fromUri("android.resource://${context.packageName}/${movie.trailer}")
    val trailerPlayer = remember {
        ExoPlayer.Builder(context).build()
    }

    // Set MediaSource to ExoPlayer
    LaunchedEffect(movieTrailer) {
        // Set the media item to be played.
        trailerPlayer.setMediaItem(movieTrailer)
        // Prepare the player.
        trailerPlayer.prepare()
    }

    // Manage lifecycle events
    DisposableEffect(Unit) {
        onDispose {
            trailerPlayer.release()
        }
    }

    // Use AndroidView to embed an Android View (PlayerView) into Compose
    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = trailerPlayer
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // Set your desired height
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