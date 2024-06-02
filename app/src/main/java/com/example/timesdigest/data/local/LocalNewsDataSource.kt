package com.example.timesdigest.data.local

import com.example.timesdigest.data.local.dao.ArticleDao
import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType
import javax.inject.Inject

class LocalNewsDataSource @Inject constructor(
    private val articleDao: ArticleDao
) {
    fun getArticlesByType(type: ArticleEntityType, isFresh: Boolean, limit: Int) =
        articleDao.observeArticlesByType(type, isFresh, limit)

    fun observeAllSavedArticles() =
        articleDao.observeAllSavedArticles()

    suspend fun getAllSavedArticles() =
        articleDao.getAllSavedArticles()

    suspend fun insertAllArticles(articles: List<ArticleEntity>) =
        articleDao.insertAllArticles(articles)

    suspend fun upsertArticle(article: ArticleEntity) =
        articleDao.upsertArticle(article)

    suspend fun deleteAllArticles() =
        articleDao.deleteAllArticles()
}