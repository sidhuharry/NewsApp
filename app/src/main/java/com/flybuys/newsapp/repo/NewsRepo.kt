package com.flybuys.newsapp.repo

import com.flybuys.newsapp.model.GenericResponse
import com.flybuys.newsapp.network.service.INewsService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Abstraction layer for future changes. You can plugin cache or room db logic here when getting network response.
 */
@Singleton
class NewsRepo @Inject constructor(private val newsService: INewsService) : INewsRepo {
    /**
     * Async function to load news items. Must be called from a coroutine.
     */
    override suspend fun getNewsItems(): Flow<GenericResponse> {
        return newsService.getNewsItems();
    }
}