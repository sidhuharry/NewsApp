package com.flybuys.newsapp.network.service

import com.flybuys.newsapp.model.NetworkResponse
import kotlinx.coroutines.flow.Flow


interface INewsService {

    fun getNewsItems(): Flow<NetworkResponse>
}