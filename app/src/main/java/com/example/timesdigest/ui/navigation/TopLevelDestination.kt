package com.example.timesdigest.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Newspaper
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.timesdigest.R

enum class TopLevelDestination(
    val route: String,
    @StringRes
    val appBarTitleId: Int,
    @StringRes
    val bottomBarTitleId: Int,
    val bottomBarIconSelected: ImageVector,
    val bottomBarIconUnselected: ImageVector,
) {
    HOME(
        route = "home",
        appBarTitleId = R.string.home_appbar_title,
        bottomBarTitleId = R.string.home_bottombar_title,
        bottomBarIconSelected = Icons.Filled.Newspaper,
        bottomBarIconUnselected = Icons.Outlined.Newspaper
    ),
    SAVED(
        route = "saved",
        appBarTitleId = R.string.saved_appbar_title,
        bottomBarTitleId = R.string.saved_appbar_title,
        bottomBarIconSelected = Icons.Filled.Bookmarks,
        bottomBarIconUnselected = Icons.Outlined.Bookmarks
    ),
    SEARCH(
        route = "search",
        appBarTitleId = R.string.search_appbar_title,
        bottomBarTitleId = R.string.search_appbar_title,
        bottomBarIconSelected = Icons.Filled.Search,
        bottomBarIconUnselected = Icons.Outlined.Search
    )
}