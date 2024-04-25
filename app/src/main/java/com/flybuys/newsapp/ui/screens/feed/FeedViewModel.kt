package com.flybuys.newsapp.ui.screens.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flybuys.newsapp.model.Feed
import com.flybuys.newsapp.model.NewsItem
import com.flybuys.newsapp.model.NewsItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FeedViewModel : ViewModel() {

    private val _isError = MutableLiveData<Boolean>()

    val isError: LiveData<Boolean>
        get() = _isError

    private val _isLoading = MutableLiveData<Boolean>()

    val isLoading: LiveData<Boolean>
        get() = _isLoading


    private val _newsItems = MutableLiveData<NewsItems>()

    val newsItems: LiveData<NewsItems>
        get() = _newsItems


    fun loadNewsItems() {

        _isLoading.value = true;
        _isError.value = false;

        viewModelScope.launch {
            delay(1000)
        }
    }

}