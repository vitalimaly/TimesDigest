package com.example.timesdigest.data.network.api

import com.example.timesdigest.data.network.response.MostPopularResponse
import com.example.timesdigest.data.network.response.SearchArticlesResponse
import com.example.timesdigest.data.network.response.TopStoriesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NYTimesService {
    @GET("mostpopular/v2/{category}/{days}.json")
    suspend fun getMostPopularArticles(
        @Path("category") category: String,
        @Path("days") days: Int
    ): MostPopularResponse

    @GET("topstories/v2/{section}.json")
    suspend fun getTopStories(
        @Path("section") section: String
    ): TopStoriesResponse

    @GET("search/v2/articlesearch.json")
    suspend fun getSearchArticles(
        @Query("fq") filterQuery: String,
        @Query("sort") sort: String
    ): SearchArticlesResponse
}