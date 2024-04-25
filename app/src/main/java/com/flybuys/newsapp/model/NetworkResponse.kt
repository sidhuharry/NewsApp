package com.flybuys.newsapp.model


/**
 * Class to encapsulate network response from the repo layer to UI View model
 */
sealed class NetworkResponse {
    data class Success(
        val responseBody: Any? = null
    ) : NetworkResponse()

    data class Failure(
        val reason: String = ""
    ) : NetworkResponse()
}