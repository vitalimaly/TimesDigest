package com.example.timesdigest.data

import com.example.timesdigest.data.local.LocalNewsDataSource
import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType.MOST_POPULAR
import com.example.timesdigest.data.local.entities.ArticleEntityType.TOP_STORY
import com.example.timesdigest.data.local.entities.asArticle
import com.example.timesdigest.data.network.MostPopularArticlesCategoryPath
import com.example.timesdigest.data.network.MostPopularArticlesCategoryPath.VIEWED
import com.example.timesdigest.data.network.MostPopularArticlesPeriodPath
import com.example.timesdigest.data.network.MostPopularArticlesPeriodPath.ONE
import com.example.timesdigest.data.network.NetworkNewsDataSource
import com.example.timesdigest.data.network.TopStoriesSectionPath
import com.example.timesdigest.data.network.TopStoriesSectionPath.HOME
import com.example.timesdigest.data.network.response.Doc
import com.example.timesdigest.data.network.response.MostPopularArticleResponse
import com.example.timesdigest.data.network.response.TopStoryResponse
import com.example.timesdigest.data.network.response.asArticle
import com.example.timesdigest.data.network.response.asEntity
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.asEntity
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val networkNewsDataSource: NetworkNewsDataSource,
    private val localNewsDataSource: LocalNewsDataSource
) {
    fun observeTopStories(isFresh: Boolean, limit: Int) =
        localNewsDataSource.getArticlesByType(type = TOP_STORY, isFresh = isFresh, limit = limit)
            .map { it.map(ArticleEntity::asArticle) }
            .filter { it.isNotEmpty() }

    fun observeMostPopularArticles(isFresh: Boolean, limit: Int) =
        localNewsDataSource.getArticlesByType(type = MOST_POPULAR, isFresh = isFresh, limit = limit)
            .map { it.map(ArticleEntity::asArticle) }
            .filter { it.isNotEmpty() }

    fun observeAllSavedArticles() =
        localNewsDataSource.observeAllSavedArticles()
            .map { it.map(ArticleEntity::asArticle) }

    suspend fun syncArticlesAndStories() {
        val mostPopularEntities = fetchMostPopularArticles()
            .map(MostPopularArticleResponse::asEntity)
        val topStoriesEntities = fetchTopStories()
            .map(TopStoryResponse::asEntity)
        val freshArticles = mostPopularEntities + topStoriesEntities
        val savedArticles = localNewsDataSource.getAllSavedArticles().onEach { it.isFresh = false }

        savedArticles.forEach { savedArticle ->
            freshArticles.forEach { freshArticle ->
                val isSameArticle = savedArticle.url == freshArticle.url
                if (isSameArticle) {
                    freshArticle.isSaved = true
                    savedArticle.isFresh = true
                }
            }
        }
        val filteredArticles = (freshArticles + savedArticles).distinctBy { it.url }
        localNewsDataSource.deleteAllArticles()
        localNewsDataSource.insertAllArticles(filteredArticles)
    }

    suspend fun updateArticle(article: Article) =
        localNewsDataSource.upsertArticle(article.asEntity())

    suspend fun fetchSearchArticles(query: String): List<Article> {
        val searchArticles = networkNewsDataSource.getSearchArticles(query).response.docs
            .map(Doc::asArticle)
        val savedArticles = localNewsDataSource.getAllSavedArticles()
        savedArticles.forEach { savedArticle ->
            searchArticles.forEach { searchArticle ->
                searchArticle.isSaved = savedArticle.url == searchArticle.url
            }
        }
        return searchArticles
    }

    private suspend fun fetchTopStories(section: TopStoriesSectionPath = HOME) =
        networkNewsDataSource.getTopStories(section).results

    private suspend fun fetchMostPopularArticles(
        category: MostPopularArticlesCategoryPath = VIEWED,
        period: MostPopularArticlesPeriodPath = ONE
    ) = networkNewsDataSource.getMostPopularArticles(category, period).results
}
