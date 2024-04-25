package com.flybuys.newsapp.network.api

import com.flybuys.newsapp.model.NewsItems
import retrofit2.Call
import retrofit2.http.GET

interface News {

    @GET("v1/api.json")
    fun getNewsItems(): Call<NewsItems>

}