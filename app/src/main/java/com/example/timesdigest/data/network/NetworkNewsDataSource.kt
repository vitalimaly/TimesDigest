package com.example.timesdigest.data.network

import com.example.timesdigest.data.network.SearchArticlesDocumentTypeFilterQuery.ARTICLE
import com.example.timesdigest.data.network.SearchArticlesInCategoriesFilterQuery.ABSTRACT
import com.example.timesdigest.data.network.SearchArticlesInCategoriesFilterQuery.DOCUMENT_TYPE
import com.example.timesdigest.data.network.SearchArticlesInCategoriesFilterQuery.HEADLINE
import com.example.timesdigest.data.network.api.NYTimesService
import javax.inject.Inject

class NetworkNewsDataSource @Inject constructor(
    private val nyTimesService: NYTimesService
) {
    suspend fun getMostPopularArticles(
        category: MostPopularArticlesCategoryPath,
        period: MostPopularArticlesPeriodPath
    ) = nyTimesService.getMostPopularArticles(category.path, period.days)

    suspend fun getTopStories(section: TopStoriesSectionPath) =
        nyTimesService.getTopStories(section.path)

    suspend fun getSearchArticles(query: String) =
        nyTimesService.getSearchArticles(
            filterQuery = "${DOCUMENT_TYPE.query}:${ARTICLE.query} AND " +
                "(${HEADLINE.query}:($query) OR ${ABSTRACT.query}:($query))",
            sort = SearchArticlesSortQuery.NEWEST.query
        )
}

enum class MostPopularArticlesCategoryPath(val path: String) {
    EMAILED("emailed"),
    SHARED("shared"),
    VIEWED("viewed")
}

enum class MostPopularArticlesPeriodPath(val days: Int) {
    ONE(1), SEVEN(7), THIRTY(30)
}

enum class TopStoriesSectionPath(val path: String) {
    HOME("home"),
    ARTS("arts"),
    AUTOMOBILES("automobiles"),
    BOOKS_REVIEW("books/review"),
    BUSINESS("business"),
    FASHION("fashion"),
    FOOD("food"),
    HEALTH("health"),
    INSIDER("insider"),
    MAGAZINE("magazine"),
    MOVIES("movies"),
    NYREGION("nyregion"),
    OBITUARIES("obituaries"),
    OPINION("opinion"),
    POLITICS("politics"),
    REALESTATE("realestate"),
    SCIENCE("science"),
    SPORTS("sports"),
    SUNDAYREVIEW("sundayreview"),
    TECHNOLOGY("technology"),
    THEATER("theater"),
    T_MAGAZINE("t-magazine"),
    TRAVEL("travel"),
    UPSHOT("upshot"),
    US("us"),
    WORLD("world")
}

enum class SearchArticlesInCategoriesFilterQuery(val query: String) {
    HEADLINE("headline"),
    ABSTRACT("abstract"),
    DOCUMENT_TYPE("document_type")
}

enum class SearchArticlesSortQuery(val query: String) {
    NEWEST("newest"),
    OLDEST("oldest"),
    RELEVANCE("relevance")
}

enum class SearchArticlesDocumentTypeFilterQuery(val query: String) {
    ARTICLE("article"),
    MULTIMEDIA("multimedia")
}