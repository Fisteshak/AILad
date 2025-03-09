package com.example.ailad.ui

import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ailad.R
import com.example.ailad.ui.chat.ChatScreen
import com.example.ailad.ui.rag.RAGScreen
import com.example.ailad.ui.settings.SettingsScreen

@Composable
fun AILadApp() {
    val navController = rememberNavController()

    val viewModel: MainViewModel = hiltViewModel()

    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    Scaffold(
        bottomBar = {

            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                topLevelRoutes.forEachIndexed { index, topLevelRoute ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painterResource(topLevelRoute.icon),
                                contentDescription = stringResource(topLevelRoute.nameId)
                            )
                        },
                        label = { Text(stringResource(topLevelRoute.nameId)) },
                        selected = let {
                            currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true

                        }

                        ,
                        onClick = {
                            navController.navigate(topLevelRoute.route) {
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
        NavHost(
            navController,
            startDestination = Chat(),
            Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            composable<Chat> { navBackStackEntry ->
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    ChatScreen(
                        onNavigateToPrompts = {
                            viewModel.selectedTab.intValue = 2
                            navController.navigate(route = RAG) {
                                launchSingleTop = true
                                popUpTo(Chat()) {
                                    saveState = true
                                    inclusive = true
                                }
                            }
                        }
                    )
                }
            }

            composable<RAG> { navBackStack ->
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    val locale = stringResource(R.string.locale)

                    RAGScreen(
                        onNavigateToChat = { id, shouldRun ->
                            if (shouldRun) {
                                viewModel.updateSearchBarText("")
                                viewModel.generate(locale, id)

                            } else {
                                viewModel.loadPromptToSearchBar(id)
                            }
                            navController.navigate(route = Chat()) {
                                launchSingleTop = true
                                popUpTo(RAG) {
                                    saveState = true
                                    inclusive = true
                                }
                            }
                        },

                        )
                }
            }
            composable<Settings> {
                CompositionLocalProvider(
                    LocalViewModelStoreOwner provides viewModelStoreOwner
                ) {
                    SettingsScreen()
                }
            }
        }
    }
}