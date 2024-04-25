package com.flybuys.newsapp.network.util

import android.util.Log
import com.flybuys.newsapp.model.NetworkResponse
import retrofit2.Response

class Parser {

    /**
     * Parse the network response into something which UI layer can understand.
     *
     * For success: NetworkResponse.Success
     * For failure: NetworkResponse.Failure
     */
    fun <T> parseResponse(result: Result<Response<T>>): NetworkResponse {
        var networkResp: NetworkResponse = NetworkResponse.Failure()
        if (result.isSuccess) {
            result.getOrNull()?.let {
                networkResp = if (it.isSuccessful) {
                    NetworkResponse.Success(it.body())
                } else {
                    NetworkResponse.Failure(it.message())
                }
            }
        } else {
            Log.e(TAG, "Unable to parse response.", result.exceptionOrNull())
        }
        return networkResp
    }

    companion object {
        private const val TAG = "Parser"
    }
}