package com.example.movieappmad24

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.NavItem

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppMAD24Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            CenterAlignedTopAppBar(title = { Text("Movie App") })
                        },
                        bottomBar = {
                            val navigationItems: List<NavItem> = mutableListOf(
                                NavItem(name = "Home", icon = Icons.Default.Home, clicked = {}), //TODO: Navigation Logic in Clicked
                                NavItem(name = "Watchlist", icon = Icons.Default.Star, clicked = {})
                            )
                            CreateNavigationBar(items = navigationItems)
                        },
                        content = { padding ->
                            (MovieList(movies = getMovies(), padding))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CreateNavigationBar(items: List<NavItem>)
{    var selectedItem by remember {
    mutableStateOf(items[0])
}

    NavigationBar {
        items.forEach{item ->
            NavigationBarItem(
                selected = if(selectedItem.name == item.name) true else false,
                onClick = {
                    //selectedItem = item //changes selected item when clicked
                    item.clicked // Navigation Logic
                          },
                icon = {
                       Icon(
                           imageVector = item.icon,
                           contentDescription = item.name
                       )
                },
                label = { Text(item.name) })
        }
    }
}

@Composable
fun MovieList(movies: List<Movie> = getMovies(), padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(movies) { movie ->
            MovieRow(movie)
        }
    }
}


@Composable
fun MovieRow(movie: Movie) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {
            MovieCardPictureAndFavorite(movie = movie)
            
            MovieCardTitleAndDetails(movie = movie)
        }
    }
}

@Composable
fun MovieCardPictureAndFavorite(movie: Movie)
{
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // https://coil-kt.github.io/coil/compose/
        AsyncImage(
            model = movie.images[0],
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
                tint = MaterialTheme.colorScheme.secondary,
                imageVector = Icons.Default.FavoriteBorder,
                contentDescription = "Add to favorites"
            )
        }
    }
}

@Composable
fun MovieCardTitleAndDetails(movie: Movie){
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
    Column(modifier = Modifier
        .padding(all = 15.dp)) {
        Row {
            Text("""Director: ${movie.director}
                |Released: ${movie.year}
                |Genre: ${movie.genre}
                |Actors: ${movie.actors}
                |Rating: ${movie.rating}
            """.trimMargin())
        }
        Row(
            modifier = Modifier
                .padding(vertical = 5.dp)
        ) {
            Divider()
        }
        Row {
            Text("Plot: ${movie.plot}")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MovieAppMAD24Theme {
        MovieList(movies = getMovies(), padding = PaddingValues(10.dp))
    }
}
