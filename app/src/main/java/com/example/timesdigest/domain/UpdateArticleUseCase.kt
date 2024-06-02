package com.example.timesdigest.domain

import com.example.timesdigest.data.NewsRepository
import com.example.timesdigest.model.Article
import javax.inject.Inject

class UpdateArticleUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(article: Article) {
        newsRepository.updateArticle(article)
    }
}