package com.example.timesdigest.domain

import com.example.timesdigest.data.NewsRepository
import javax.inject.Inject

class FetchSearchArticlesUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(query: String) = newsRepository.fetchSearchArticles(query)
}