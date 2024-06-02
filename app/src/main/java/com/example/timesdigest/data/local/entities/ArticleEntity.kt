package com.example.timesdigest.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.timesdigest.model.Article
import com.example.timesdigest.model.ArticleType

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    @ColumnInfo val title: String,
    val subtitle: String,
    val imageUrl: String?,
    val type: ArticleEntityType,
    var isSaved: Boolean,
    var isFresh: Boolean,
)

enum class ArticleEntityType {
    MOST_POPULAR, TOP_STORY, SEARCH
}

fun ArticleEntity.asArticle() = Article(
    title = title,
    subtitle = subtitle,
    url = url,
    imageUrl = imageUrl,
    type = type.asArticleType(),
    isSaved = isSaved,
    isFresh = isFresh
)

fun ArticleEntityType.asArticleType() = when (this) {
    ArticleEntityType.MOST_POPULAR -> ArticleType.MOST_POPULAR
    ArticleEntityType.TOP_STORY -> ArticleType.TOP_STORY
    ArticleEntityType.SEARCH -> ArticleType.SEARCH
}
