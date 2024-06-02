package com.example.timesdigest.ui.home

import androidx.annotation.StringRes
import com.example.timesdigest.model.HomeFeed

sealed interface HomeUiState {
    data class Success(val feed: HomeFeed) : HomeUiState
    data class Error(@StringRes val errorResId: Int) : HomeUiState
    data object Loading : HomeUiState
}
