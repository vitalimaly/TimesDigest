package com.example.timesdigest.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.timesdigest.R

enum class TopLevelDestination(
    val route: String,
    @StringRes
    val appBarTitleId: Int,
    @StringRes
    val bottomBarTitleId: Int,
    @DrawableRes
    val bottomBarIconSelected: Int,
    @DrawableRes
    val bottomBarIconUnselected: Int,
) {
    HOME(
        route = "home",
        appBarTitleId = R.string.home_appbar_title,
        bottomBarTitleId = R.string.home_bottombar_title,
        bottomBarIconSelected = R.drawable.news_fill,
        bottomBarIconUnselected = R.drawable.news_outline
    ),
    SAVED(
        route = "saved",
        appBarTitleId = R.string.saved_appbar_title,
        bottomBarTitleId = R.string.saved_appbar_title,
        bottomBarIconSelected = R.drawable.bookmark_fill,
        bottomBarIconUnselected = R.drawable.bookmark_outline
    ),
    SEARCH(
        route = "search",
        appBarTitleId = R.string.search_appbar_title,
        bottomBarTitleId = R.string.search_appbar_title,
        bottomBarIconSelected = R.drawable.search,
        bottomBarIconUnselected = R.drawable.search
    )
}