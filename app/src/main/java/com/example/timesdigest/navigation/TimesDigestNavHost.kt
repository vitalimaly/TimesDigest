package com.example.timesdigest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.timesdigest.ui.TimesDigestAppState
import com.example.timesdigest.ui.home.HomeScreen
import com.example.timesdigest.ui.saved.SavedScreen
import com.example.timesdigest.ui.search.SearchScreen

@Composable
fun TimesDigestNavHost(
    appState: TimesDigestAppState,
    modifier: Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = TopLevelDestination.HOME.route,
        modifier = modifier
    ) {
        composable(route = TopLevelDestination.HOME.route) {
            HomeScreen()
        }
        composable(
            route = TopLevelDestination.SAVED.route,
        ) {
            SavedScreen()
        }
        composable(
            route = TopLevelDestination.SEARCH.route,
        ) {
            SearchScreen()
        }
    }
}