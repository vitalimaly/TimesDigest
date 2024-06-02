package com.example.timesdigest.model

import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType

data class Article(
    val title: String,
    val subtitle: String,
    val url: String,
    val imageUrl: String?,
    val type: ArticleType,
    var isSaved: Boolean,
    var isFresh: Boolean,
)

enum class ArticleType {
    MOST_POPULAR, TOP_STORY, SEARCH
}

fun Article.asEntity() = ArticleEntity(
    url = url,
    title = title,
    subtitle = subtitle,
    imageUrl = imageUrl,
    type = type.asEntity(),
    isSaved = isSaved,
    isFresh = isFresh
)

fun ArticleType.asEntity() = when (this) {
    ArticleType.MOST_POPULAR -> ArticleEntityType.MOST_POPULAR
    ArticleType.TOP_STORY -> ArticleEntityType.TOP_STORY
    ArticleType.SEARCH -> ArticleEntityType.SEARCH
}
