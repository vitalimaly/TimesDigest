package com.example.timesdigest.model

data class HomeFeed(
    val mainTopStory: Article,
    val secondaryTopStory: List<Article>,
    val popularArticles: List<Article>
)

