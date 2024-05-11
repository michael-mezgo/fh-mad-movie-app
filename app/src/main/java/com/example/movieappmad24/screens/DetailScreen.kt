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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieappmad24.R
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.repositories.MovieRepository
import com.example.movieappmad24.viewmodels.DetailViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory
import com.example.movieappmad24.widgets.CustomTopAppBar
import com.example.movieappmad24.widgets.MovieCard
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(movieId: Long?, navController: NavController) {

    val db : MovieDatabase = MovieDatabase.getDatabase(LocalContext.current)
    val repository: MovieRepository = MovieRepository(movieDao = db.movieDao())
    val factory: MoviesViewModelFactory = MoviesViewModelFactory(repository = repository)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    val movieWithImages = viewModel.findMovieWithId(movieId)

    val coroutineScope = rememberCoroutineScope()

    if (movieWithImages == null) {
        Text("Movie not found")
        return
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        CustomTopAppBar(title = movieWithImages.movie.title, navController = navController)
    },

        content = { paddingValues ->
            Box(
                modifier = Modifier.padding(paddingValues)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Row {
                        MovieCard(movie = movieWithImages.movie,
                            onFavoriteClick = {
                                coroutineScope.launch {
                                    viewModel.toggleIsFavorite(movieWithImages.movie)
                                }
                            })
                    }
                    Row {
                        Text("Movie trailer")
                    }
                    Row {
                        TrailerPlayer(movie = movieWithImages.movie)
                    }
                    LazyRow(
                        Modifier.height(300.dp)
                    ) {
                        this.items(items = movieWithImages.movieImages) { image ->
                            MoviePicture(resourceLink = image.url, title = movieWithImages.movie.title)
                        }
                    }
                }
            }
        })
}

@Composable
fun TrailerPlayer(movie: Movie) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }

    var videoPaused by remember {
        mutableStateOf(value = true)
    }

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

    val lifecycleOwner = LocalLifecycleOwner.current

    // Source: https://youtu.be/NpCSzl74ciY?si=SVsV_CB3B0pfKGKP (from Moodle Course)
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer = observer)

        onDispose {
            trailerPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer = observer)
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
            .aspectRatio(16f / 9f),
        // Source: https://youtu.be/NpCSzl74ciY?si=SVsV_CB3B0pfKGKP (from Moodle Course)
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                    if (!videoPaused) {
                        trailerPlayer.play()
                    }
                }

                Lifecycle.Event.ON_STOP -> {
                    it.onPause()
                    videoPaused = !trailerPlayer.isPlaying
                    trailerPlayer.pause()
                }

                else -> Unit
            }
        },
    )
}

@Composable
fun MoviePicture(resourceLink: String, title: String) {
    Card(
        shape = RoundedCornerShape(size = 20.dp), modifier = Modifier.padding(all = 15.dp)
    ) {
        AsyncImage(
            model = resourceLink,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.movie_image),
            contentDescription = "$title Image",
            modifier = Modifier.aspectRatio(ratio = 1f)
        )
    }
}