package com.example.timesdigest.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.pinnedScrollBehavior
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.timesdigest.R
import com.example.timesdigest.ui.navigation.TimesDigestNavHost
import com.example.timesdigest.ui.navigation.TopLevelDestination
import com.example.timesdigest.ui.settings.SettingsDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimesDigestApp(
    appState: TimesDigestAppState = rememberTimesDigestAppState()
) {
    var showSettingsDialog by rememberSaveable { mutableStateOf(false) }
    if (showSettingsDialog) {
        SettingsDialog(
            onDismiss = { showSettingsDialog = false },
        )
    }

    val scrollBehavior = pinnedScrollBehavior()
    Scaffold(
        topBar = {
            val destination = appState.currentTopLevelDestination
            if (destination != null) {
                TimesDigestAppBar(
                    title = stringResource(destination.appBarTitleId),
                    scrollBehavior = scrollBehavior,
                    onSettingsClick = { showSettingsDialog = true }
                )
            }
        },
        bottomBar = {
            TimesDigestBottomBar(
                destinations = TopLevelDestination.entries,
                onNavigateToDestination = appState::navigateToTopLevelDestination,
                currentDestination = appState.currentDestination
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        TimesDigestNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimesDigestAppBar(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}

@Composable
fun TimesDigestBottomBar(
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
    modifier: Modifier = Modifier,
) {
    NavigationBar(
        modifier = modifier,
    ) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val icon = if (selected) {
                        destination.bottomBarIconSelected
                    } else {
                        destination.bottomBarIconUnselected
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null
                    )
                },
                label = {
                    val fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    Text(
                        text = stringResource(destination.bottomBarTitleId),
                        fontWeight = fontWeight
                    )
                }
            )
        }
    }
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.name, true) ?: false
    } ?: false
