package com.example.ailad.ui

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

@Serializable
data class Chat(
    val promptId: Int? = null,
    val shouldRun: Boolean = false,
) {
    init {
        Log.d("Chat ROute", "creating: $promptId, $shouldRun")
    }
}

@Serializable
object RAG

@Serializable
object Settings


val topLevelRoutes = listOf(
    TopLevelRoute("Chat", Chat(), Icons.Default.MailOutline),
    TopLevelRoute("RAG", RAG, Icons.Default.Search),
    TopLevelRoute("Settings", Settings, Icons.Default.Settings)
)