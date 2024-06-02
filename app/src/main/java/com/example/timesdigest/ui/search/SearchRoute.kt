package com.example.timesdigest.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchUiState by viewModel.searchUiState.collectAsStateWithLifecycle()
    SearchScreen(
        searchUiState = searchUiState,
        onSaveArticleClick = viewModel::onSaveArticleClick,
        onSearchTriggered = viewModel::onSearchTriggered,
    )
}