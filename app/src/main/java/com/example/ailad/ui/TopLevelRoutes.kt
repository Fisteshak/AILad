package com.example.ailad.ui

import com.example.ailad.R
import kotlinx.serialization.Serializable

data class TopLevelRoute<T : Any>(val nameId: Int, val route: T, val icon: Int)

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
    TopLevelRoute(R.string.chat_bottom_navbar, Chat(), R.drawable.baseline_chat_24),
    TopLevelRoute(R.string.RAG_bottom_navbar, RAG, R.drawable.baseline_library_books_24),
    TopLevelRoute(R.string.settings_bottom_navbar, Settings, R.drawable.baseline_settings_24)
)