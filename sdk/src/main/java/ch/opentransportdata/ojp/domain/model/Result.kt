package ch.opentransportdata.ojp.domain.model

import ch.opentransportdata.ojp.domain.model.error.OjpError

/**
 * Created by Deniz Kalem on 06.05.2024
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val error: OjpError) : Result<Nothing>()
}