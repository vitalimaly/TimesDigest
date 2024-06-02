package com.example.timesdigest.ui.search

import androidx.annotation.StringRes
import com.example.timesdigest.model.Article

sealed interface SearchUiState {
    /**
     * The state query is empty or too short. To distinguish the state between the
     * (initial state or when the search query is cleared) vs the state where no search
     * result is returned, explicitly define the empty query state.
     */
    data object EmptyQuery : SearchUiState
    data class Success(val articles: List<Article>) : SearchUiState
    data object Loading : SearchUiState
    data class Error(@StringRes val errorResId: Int) : SearchUiState
}
