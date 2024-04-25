package com.flybuys.newsapp.model


/**
 * Class to encapsulate network response from the repo layer to UI View model
 */
sealed class GenericResponse {
    data class Success(
        val responseBody: Any? = null
    ) : GenericResponse()

    data class Failure(
        val reason: String = ""
    ) : GenericResponse()
}