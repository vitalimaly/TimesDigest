package com.example.timesdigest.ui.saved

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SavedRoute(
    viewModel: SavedViewModel = hiltViewModel()
) {
    val savedUiState by viewModel.savedUiState.collectAsStateWithLifecycle()
    SavedScreen(
        savedUiState = savedUiState,
        onSaveArticleClick = viewModel::onSaveArticleClick
    )
}