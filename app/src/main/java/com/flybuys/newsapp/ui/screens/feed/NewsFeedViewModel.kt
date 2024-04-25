package com.flybuys.newsapp.ui.screens.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flybuys.newsapp.model.Feed
import com.flybuys.newsapp.model.GenericResponse
import com.flybuys.newsapp.model.NewsItems
import com.flybuys.newsapp.repo.INewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(private val newsRepo: INewsRepo) : ViewModel() {

    private val _isError = MutableStateFlow(false)
    val isError: StateFlow<Boolean> = _isError.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()


    private val _newsItems = MutableStateFlow(NewsItems("loading", Feed("News", ""), arrayOf()))
    val newsItems: StateFlow<NewsItems> = _newsItems.asStateFlow()


    fun loadNewsItems() {

        Log.d(TAG, "loadNewsItems: start")

        _isLoading.value = true;
        _isError.value = false;

        viewModelScope.launch(Dispatchers.IO) {
            Log.d(TAG, "loadNewsItems: launching coroutine")
            newsRepo.getNewsItems().collect {
                _isLoading.value = false
                when (it) {
                    is GenericResponse.Failure -> {
                        _isError.value = true
                    }

                    is GenericResponse.Success -> {
                        _isError.value = false

                        // post the list to the view model
                        val resp = it.responseBody as NewsItems

                        for (item in resp.items) {
                            item.pubDate = fixDateFormat(item.pubDate).orEmpty()
                        }
                        _newsItems.value = resp
                        Log.d(TAG, "loadNewsItems: done")
                    }
                }
            }
        }
    }

    private fun fixDateFormat(inputDateStr: String): String? {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMMM dd, yyyy h:mm a", Locale.getDefault())

        return inputDateFormat.parse(inputDateStr)?.let { outputDateFormat.format(it) }
    }

    companion object {
        private const val TAG = "FeedViewModel"
    }

}