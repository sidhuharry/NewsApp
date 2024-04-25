package com.flybuys.newsapp.network.service

import com.flybuys.newsapp.model.NetworkResponse
import kotlinx.coroutines.flow.Flow

class NewsServiceImpl : INewsService {
    override fun getNewsItems(): Flow<NetworkResponse> {
        TODO("Not yet implemented")
    }
}