package ch.opentransportdata.ojp.domain.model

/**
 * Created by Deniz Kalem on 06.05.2024
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: ch.opentransportdata.ojp.domain.model.error.Error) : Result<Nothing>()
}