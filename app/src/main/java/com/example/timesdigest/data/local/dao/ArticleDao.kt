package com.example.timesdigest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.timesdigest.data.local.entities.ArticleEntity
import com.example.timesdigest.data.local.entities.ArticleEntityType
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles WHERE type IS :type AND isFresh IS :isFresh LIMIT :limit")
    fun observeArticlesByType(
        type: ArticleEntityType,
        isFresh: Boolean,
        limit: Int
    ): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE isSaved = 1")
    fun observeAllSavedArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE isSaved = 1")
    suspend fun getAllSavedArticles(): List<ArticleEntity>

    @Query("UPDATE articles SET isSaved = :isSaved WHERE url = :url")
    suspend fun updateArticleIsSaved(url: String, isSaved: Boolean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticles(articles: List<ArticleEntity>)

    @Upsert
    suspend fun upsertArticle(article: ArticleEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles WHERE  isSaved = 0")
    suspend fun deleteAllNotSavedArticles()

    @Query("DELETE FROM articles")
    suspend fun deleteAllArticles()
}