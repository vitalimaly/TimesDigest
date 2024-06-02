package com.example.timesdigest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timesdigest.R
import com.example.timesdigest.domain.FetchHomeNewsFeedUseCase
import com.example.timesdigest.domain.ObserveHomeNewsFeedUseCase
import com.example.timesdigest.domain.Result
import com.example.timesdigest.domain.UpdateArticleUseCase
import com.example.timesdigest.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class HomeViewModel @Inject constructor(
    observeHomeNewsFeedUseCase: ObserveHomeNewsFeedUseCase,
    private val fetchHomeNewsFeedUseCase: FetchHomeNewsFeedUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase
) : ViewModel() {
    private val isLoading = MutableStateFlow(false)
    private val error: MutableStateFlow<Result.Error?> = MutableStateFlow(null)

    val homeUiState: StateFlow<HomeUiState> = combine(
        isLoading, error, observeHomeNewsFeedUseCase()
    ) { isLoading, error, homeFeedResult ->
        if (isLoading) return@combine HomeUiState.Loading
        if (error != null) return@combine HomeUiState.Error(R.string.refresh_error)
        return@combine when (homeFeedResult) {
            is Result.Success -> HomeUiState.Success(homeFeedResult.data)
            is Result.Loading -> HomeUiState.Loading
            is Result.Error -> HomeUiState.Error(R.string.loading_articles_error)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = HomeUiState.Loading
    )

    init {
        refresh()
    }

    fun onSaveArticleClick(article: Article) {
        viewModelScope.launch {
            updateArticleUseCase(article.copy(isSaved = !article.isSaved))
        }
    }

    private fun refresh() {
        isLoading.value = true
        viewModelScope.launch {
            val result = fetchHomeNewsFeedUseCase()
            if (result is Result.Error) {
                error.value = result
            }
            isLoading.value = false
        }
    }
}
