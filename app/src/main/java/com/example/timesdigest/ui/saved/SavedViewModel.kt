package com.example.timesdigest.ui.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timesdigest.domain.ObserveSavedArticlesUseCase
import com.example.timesdigest.domain.UpdateArticleUseCase
import com.example.timesdigest.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class SavedViewModel @Inject constructor(
    observeSavedArticlesUseCase: ObserveSavedArticlesUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase
) : ViewModel() {
    val savedUiState = observeSavedArticlesUseCase()
        .map(SavedUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = SavedUiState.Loading
        )

    fun onSaveArticleClick(article: Article) {
        viewModelScope.launch {
            updateArticleUseCase(article.copy(isSaved = !article.isSaved))
        }
    }
}
