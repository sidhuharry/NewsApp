package com.flybuys.newsapp.di

import com.flybuys.newsapp.network.api.NewsApi
import com.flybuys.newsapp.network.service.INewsService
import com.flybuys.newsapp.network.service.NewsService
import com.flybuys.newsapp.repo.INewsRepo
import com.flybuys.newsapp.repo.NewsRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun providesNewsApi(): NewsApi {
        return Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(" https://api.rss2json.com/").build()
            .create(NewsApi::class.java)
    }

    @Provides
    @Singleton
    fun providesNewsService(newsApi: NewsApi): INewsService {
        return NewsService(newsApi)
    }

    @Provides
    @Singleton
    fun providesNewsRepo(newsService: INewsService): INewsRepo {
        return NewsRepo(newsService)
    }
}