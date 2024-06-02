package com.example.timesdigest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.timesdigest.ui.TimesDigestAppState
import com.example.timesdigest.ui.home.HomeRoute
import com.example.timesdigest.ui.navigation.TopLevelDestination.HOME
import com.example.timesdigest.ui.navigation.TopLevelDestination.SAVED
import com.example.timesdigest.ui.navigation.TopLevelDestination.SEARCH
import com.example.timesdigest.ui.saved.SavedRoute
import com.example.timesdigest.ui.search.SearchRoute

@Composable
fun TimesDigestNavHost(
    appState: TimesDigestAppState,
    modifier: Modifier
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = HOME.route,
        modifier = modifier
    ) {
        composable(route = HOME.route) {
            HomeRoute()
        }
        composable(route = SAVED.route) {
            SavedRoute()
        }
        composable(route = SEARCH.route) {
            SearchRoute()
        }
    }
}