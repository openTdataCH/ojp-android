package ch.opentransportdata.ojp.domain.model

sealed class Response<out T> {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val error: Exception) : Response<Nothing>()
}