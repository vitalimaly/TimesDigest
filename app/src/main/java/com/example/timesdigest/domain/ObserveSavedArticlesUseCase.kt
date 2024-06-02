package com.example.timesdigest.domain

import com.example.timesdigest.data.NewsRepository
import javax.inject.Inject

class ObserveSavedArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() = newsRepository.observeAllSavedArticles()
}