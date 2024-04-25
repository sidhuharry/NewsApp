package com.flybuys.newsapp.network.api

import com.flybuys.newsapp.model.NewsItems
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("v1/api.json")
    fun getNewsItems(@Query("rss_url") rssUrl: String): Call<NewsItems>

}