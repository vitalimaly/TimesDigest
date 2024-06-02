package com.example.timesdigest.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.timesdigest.R
import com.example.timesdigest.domain.FetchSearchArticlesUseCase
import com.example.timesdigest.domain.Result.Error
import com.example.timesdigest.domain.Result.Loading
import com.example.timesdigest.domain.Result.Success
import com.example.timesdigest.domain.UpdateArticleUseCase
import com.example.timesdigest.domain.asFlowResult
import com.example.timesdigest.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    fetchSearchArticlesUseCase: FetchSearchArticlesUseCase,
    private val updateArticleUseCase: UpdateArticleUseCase
) : ViewModel() {
    private val searchQuery = MutableStateFlow("")

    val searchUiState: StateFlow<SearchUiState> = searchQuery.map {
        it.trim()
    }.flatMapLatest {
        if (it.length < SEARCH_QUERY_MIN_LENGTH) {
            flowOf(SearchUiState.EmptyQuery)
        } else {
            asFlowResult { fetchSearchArticlesUseCase(it) }
                .map { result ->
                    when (result) {
                        is Success -> SearchUiState.Success(result.data)
                        is Loading -> SearchUiState.Loading
                        is Error -> {
                            Log.e(
                                this::class.simpleName,
                                result.exception.message,
                                result.exception
                            )
                            SearchUiState.Error(R.string.search_articles_error)
                        }
                    }
                }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = SearchUiState.Loading
    )

    fun onSearchTriggered(query: String) {
        searchQuery.value = query
    }

    fun onSaveArticleClick(article: Article) {
        viewModelScope.launch {
            try {
                updateArticleUseCase(article.copy(isSaved = !article.isSaved))
            } catch (e: Exception) {
                Log.e(this::class.simpleName, e.message, e)
            }
        }
    }
}

private const val SEARCH_QUERY_MIN_LENGTH = 2
