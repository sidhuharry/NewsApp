package com.flybuys.newsapp.network.service

import com.flybuys.newsapp.model.GenericResponse
import kotlinx.coroutines.flow.Flow


interface INewsService {
    fun getNewsItems(): Flow<GenericResponse>
}