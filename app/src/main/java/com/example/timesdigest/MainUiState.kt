package com.example.timesdigest

import com.example.timesdigest.model.Settings

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Success(val settings: Settings) : MainUiState
}