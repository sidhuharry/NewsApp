package com.flybuys.newsapp.model


/**
 * Class to encapsulate network response from the repo layer to UI View model
 */
sealed class Response {
    data class Success(
        val responseBody: Any? = null
    ) : Response()

    data class Failure(
        val reason: String = ""
    ) : Response()
}