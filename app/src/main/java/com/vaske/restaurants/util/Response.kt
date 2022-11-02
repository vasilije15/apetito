package com.vaske.restaurants.util

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Response<T>(data)
    class Error<T>(message: String?, data: T? = null) : Response<T>(data, message)
    class Loading<T>(data: T? = null) : Response<T>(data)
    class Empty<T>(data: T? = null) : Response<T>(data)
}
