package com.flybuys.newsapp.di

import com.flybuys.newsapp.network.api.NewsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApi {
        return Retrofit.Builder().baseUrl(" https://api.rss2json.com").build()
            .create(NewsApi::class.java)
    }
}