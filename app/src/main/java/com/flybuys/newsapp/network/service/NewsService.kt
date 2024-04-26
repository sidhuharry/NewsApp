package com.flybuys.newsapp.network.service

import android.util.Log
import com.flybuys.newsapp.model.GenericResponse
import com.flybuys.newsapp.network.api.NewsApi
import com.flybuys.newsapp.network.util.parseRetrofitResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Singleton

@Singleton
class NewsService(
    private val newsApi: NewsApi,
) : INewsService {

    override fun getNewsItems(): Flow<GenericResponse> {
        return flow {
            val result = kotlin.runCatching {
                newsApi.getNewsItems(FEED_URL).execute()
            }
            val networkResponse = parseRetrofitResponse(result)
            emit(networkResponse)
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val FEED_URL =
            "https://www.abc.net.au/news/feed/51120/rss.xml"
        private const val TAG = "NewsService"
    }
}