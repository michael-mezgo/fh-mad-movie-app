package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.ui.theme.MovieAppMAD24Theme
import com.example.movieappmad24.ui.theme.NavItem
import com.example.movieappmad24.widgets.MovieCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Movie App") })
        },
        bottomBar = {
            val navigationItems: List<NavItem> = mutableListOf(
                NavItem(
                    name = "Home",
                    icon = Icons.Default.Home,
                    clicked = {}), //TODO: Navigation Logic in Clicked
                NavItem(name = "Watchlist", icon = Icons.Default.Star, clicked = {})
            )
            CreateNavigationBar(items = navigationItems)
        },
        content = { padding ->
            (MovieList(movies = getMovies(), padding, navController))
        }
    )
}

@Composable
fun CreateNavigationBar(items: List<NavItem>) {
    if (items.isEmpty())
        return

    var selectedItem by remember { // purposely val (Line 72 will be uncommented when navigation is used)
        mutableStateOf(items[0])
    }

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = selectedItem.name == item.name,
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
fun MovieList(
    movies: List<Movie> = getMovies(),
    padding: PaddingValues,
    navController: NavController
) {
    LazyColumn(
        modifier = Modifier.padding(padding)
    ) {
        items(movies) { movie ->
            MovieCard(movie) { movieId -> navController.navigate("detailscreen/$movieId") }
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MovieAppMAD24Theme {
        MovieList(movies = getMovies(), padding = PaddingValues(10.dp), rememberNavController())
    }
}