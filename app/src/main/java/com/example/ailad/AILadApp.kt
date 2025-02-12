package com.example.ailad

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ailad.chat.ChatScreen
import com.example.ailad.rag.RAGScreen
import com.example.ailad.settings.SettingsScreen

@Composable
fun AILadApp() {
    val navController = rememberNavController()
    var selectedItem by remember { mutableIntStateOf(0) }
    Scaffold(
        bottomBar = {

            NavigationBar {
                topLevelRoutes.forEachIndexed { index, route ->
                    NavigationBarItem(
                        icon = { Icon(route.icon, contentDescription = route.name) },
                        label = { Text(route.name) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(route.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Chat, Modifier.padding(innerPadding)) {
            composable<Chat> { ChatScreen() }
            composable<RAG> { RAGScreen() }
            composable<Settings> { SettingsScreen() }
        }
    }
}