package com.flybuys.newsapp.repo

import com.flybuys.newsapp.model.GenericResponse
import kotlinx.coroutines.flow.Flow

/**
 * Repo layer to encapsulate interaction with network.
 *
 * This can be used to plug in caches or room Db in case network response has failed
 */
interface INewsRepo {
    suspend fun getNewsItems(): Flow<GenericResponse>
}