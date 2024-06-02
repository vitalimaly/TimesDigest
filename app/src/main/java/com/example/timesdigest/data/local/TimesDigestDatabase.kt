package com.example.timesdigest.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.timesdigest.data.local.dao.ArticleDao
import com.example.timesdigest.data.local.entities.ArticleEntity

@Database(
    entities = [ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TimesDigestDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
}