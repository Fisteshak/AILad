package com.example.ailad

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

@Serializable
object Chat
@Serializable
object RAG
@Serializable
object Settings


val topLevelRoutes = listOf(
    TopLevelRoute("Chat", Chat, Icons.Default.MailOutline),
    TopLevelRoute("RAG", RAG, Icons.Default.Search),
    TopLevelRoute("Settings", Settings, Icons.Default.Settings)
)