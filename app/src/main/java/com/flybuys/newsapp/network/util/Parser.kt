package com.flybuys.newsapp.network.util

import android.util.Log
import retrofit2.Response
import javax.inject.Inject
import com.flybuys.newsapp.model.GenericResponse
import javax.inject.Singleton

@Singleton
class Parser @Inject constructor() {

    /**
     * Parse the retrofit network response into something which UI layer can understand.
     *
     * For success: NetworkResponse.Success
     * For failure: NetworkResponse.Failure
     */
    fun <T> parseRetrofitResponse(result: Result<Response<T>>): GenericResponse {
        var genericResponse: GenericResponse = GenericResponse.Failure()
        if (result.isSuccess) {
            result.getOrNull()?.let {
                genericResponse = if (it.isSuccessful) {
                    GenericResponse.Success(it.body())
                } else {
                    GenericResponse.Failure(it.message())
                }
            }
        } else {
            Log.e(TAG, "Call failed", result.exceptionOrNull())
        }
        return genericResponse
    }

    companion object {
        private const val TAG = "Parser"
    }
}