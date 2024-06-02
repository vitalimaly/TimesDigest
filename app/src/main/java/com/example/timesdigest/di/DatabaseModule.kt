package com.example.timesdigest.di

import android.content.Context
import androidx.room.Room
import com.example.timesdigest.data.local.TimesDigestDatabase
import com.example.timesdigest.data.local.dao.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesNiaDatabase(
        @ApplicationContext context: Context,
    ): TimesDigestDatabase = Room.databaseBuilder(
        context,
        TimesDigestDatabase::class.java,
        "times-digest-database",
    ).build()

    @Provides
    fun providesArticleDao(
        database: TimesDigestDatabase,
    ): ArticleDao = database.articleDao()
}