package com.flybuys.newsapp.network.service

import com.flybuys.newsapp.model.Response
import com.flybuys.newsapp.network.api.News
import com.flybuys.newsapp.network.util.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsService(
    private val newsApi: News,
    private val parser: Parser,
) : INewsService {
    override fun getNewsItems(): Flow<Response> {
        return flow {
            val result = kotlin.runCatching {
                newsApi.getNewsItems().execute()
            }
            val networkResponse = parser.parseResponse(result)
            emit(networkResponse)
        }.flowOn(Dispatchers.IO)
    }
}