package com.example.movieappmad24.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val name: String,
    val icon: ImageVector,
    val clicked: () -> Unit
)
