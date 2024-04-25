package com.flybuys.newsapp.repo

import com.flybuys.newsapp.model.Response
import com.flybuys.newsapp.network.service.INewsService
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction layer for future changes. You can plugin cache or room db logic here when getting network response.
 */
class NewsRepo(private val newsService: INewsService) : INewsRepo {
    /**
     * Async function to load news items. Must be called from a coroutine.
     */
    override suspend fun getNewsItems(): Flow<Response> {
        return newsService.getNewsItems();
    }
}