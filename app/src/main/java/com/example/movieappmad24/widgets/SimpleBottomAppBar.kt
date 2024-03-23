package com.example.movieappmad24.widgets

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.ui.theme.NavItem

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navigationItems: List<NavItem> = mutableListOf(
        NavItem(
            name = "Home",
            icon = Icons.Default.Home,
            route = Screen.Home.route
        ),
        NavItem(
            name = "Watchlist",
            icon = Icons.Default.Star,
            route = Screen.Watchlist.route
        )
    )
    CreateNavigationBar(items = navigationItems, navController)
}

@Composable
fun CreateNavigationBar(items: List<NavItem>, navController: NavController) {
    if (items.isEmpty())
        return

    val navBackStackEntry by navController.currentBackStackEntryAsState()

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                // https://stackoverflow.com/a/67603294/21992499
                selected = item.route == navBackStackEntry?.destination?.route,
                onClick = {
                    if(item.route != navBackStackEntry?.destination?.route)
                        navController.navigate(item.route)
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