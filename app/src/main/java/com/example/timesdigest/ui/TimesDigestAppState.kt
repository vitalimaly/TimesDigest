package com.example.timesdigest.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.timesdigest.navigation.TopLevelDestination
import com.example.timesdigest.navigation.TopLevelDestination.HOME
import com.example.timesdigest.navigation.TopLevelDestination.SAVED
import com.example.timesdigest.navigation.TopLevelDestination.SEARCH

@Composable
fun rememberTimesDigestAppState(): TimesDigestAppState {
    val navController = rememberNavController()
    return remember(navController) {
        TimesDigestAppState(navController)
    }
}

class TimesDigestAppState(
    val navController: NavHostController
) {
    val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination
    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            HOME.route -> HOME
            SAVED.route -> SAVED
            SEARCH.route -> SEARCH
            else -> null
        }

    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
        when (topLevelDestination) {
            HOME -> navController.navigate(HOME.route, navOptions)
            SAVED -> navController.navigate(SAVED.route, navOptions)
            SEARCH -> navController.navigate(SEARCH.route, navOptions)
        }
    }
}