package com.example.timesdigest.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        homeUiState = homeUiState,
        onSaveArticleClick = viewModel::onSaveArticleClick
    )
}