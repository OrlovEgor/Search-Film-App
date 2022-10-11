package ru.orlovegor.search_film_app.utils

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T) : ResultWrapper<T>()
    data class Error(val exception: Throwable?) : ResultWrapper<Nothing>()
    object NetworkError : ResultWrapper<Nothing>()
}
