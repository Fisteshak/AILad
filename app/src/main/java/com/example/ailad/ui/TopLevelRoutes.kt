package com.example.ailad.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.ailad.R
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(val nameId: Int, val route: T, val icon: ImageVector)

@Serializable
data class Chat(
    val promptId: Int? = null,
    val shouldRun: Boolean = false,
)

@Serializable
object RAG

@Serializable
object Settings


val topLevelRoutes = listOf(
    TopLevelRoute(R.string.chat_bottom_navbar, Chat(), Icons.Default.MailOutline),
    TopLevelRoute(R.string.RAG_bottom_navbar, RAG, Icons.Default.Search),
    TopLevelRoute(R.string.settings_bottom_navbar, Settings, Icons.Default.Settings)
)