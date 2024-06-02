package com.example.timesdigest.ui.saved

import com.example.timesdigest.model.Article

sealed interface SavedUiState {
    data class Success(val articles: List<Article>) : SavedUiState
    data object Empty : SavedUiState
    data object Loading : SavedUiState
}
