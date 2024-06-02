package com.example.timesdigest.domain

import android.util.Log
import com.example.timesdigest.data.NewsRepository
import javax.inject.Inject

class FetchHomeNewsFeedUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            newsRepository.syncArticlesAndStories()
            Result.Success(Unit)
        } catch (e: Exception) {
            Log.e(this::class.simpleName, e.message, e)
            Result.Error(e)
        }
    }
}