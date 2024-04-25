package com.flybuys.newsapp.network.service

import com.flybuys.newsapp.model.GenericResponse
import com.flybuys.newsapp.network.api.NewsApi
import com.flybuys.newsapp.network.util.Parser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsService
@Inject constructor(
    private val newsApi: NewsApi,
    private val parser: Parser,
) : INewsService {

    override fun getNewsItems(): Flow<GenericResponse> {
        return flow {
            val result = kotlin.runCatching {
                newsApi.getNewsItems(FEED_URL_ENCODED).execute()
            }
            val networkResponse = parser.parseRetrofitResponse(result)
            emit(networkResponse)
        }.flowOn(Dispatchers.IO)
    }

    companion object {
        private const val FEED_URL_ENCODED =
            "https%3A%2F%2Fwww.abc.net.au%2Fnews%2Ffeed%2F51120%2Frss.xml"
    }
}