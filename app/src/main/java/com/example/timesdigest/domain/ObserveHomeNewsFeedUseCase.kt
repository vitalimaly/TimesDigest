package com.example.timesdigest.domain

import com.example.timesdigest.data.NewsRepository
import com.example.timesdigest.model.HomeFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ObserveHomeNewsFeedUseCase @Inject constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Flow<Result<HomeFeed>> {
        return combine(
            newsRepository.observeTopStories(isFresh = true, limit = TOP_STORIES_LIMIT),
            newsRepository.observeMostPopularArticles(isFresh = true, limit = MOST_POPULAR_LIMIT)
        ) { topStories, mostPopular ->
            if (topStories.isNotEmpty()) {
                HomeFeed(
                    topStories.first(),
                    topStories.subList(1, topStories.size),
                    mostPopular
                )
            } else {
                throw IllegalStateException("Should be at least 1 top story")
            }
        }.asResult()
    }

    companion object {
        private const val TOP_STORIES_LIMIT = 4
        private const val MOST_POPULAR_LIMIT = 20
    }
}