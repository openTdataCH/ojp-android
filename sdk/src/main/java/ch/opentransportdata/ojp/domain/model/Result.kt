package ch.opentransportdata.ojp.domain.model

import ch.opentransportdata.ojp.domain.model.error.Error

/**
 * Created by Deniz Kalem on 06.05.2024
 */
sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : ch.opentransportdata.ojp.domain.model.error.Error>(val error: E) : Result<Nothing, E>
}

typealias EmptyResult<E> = Result<Unit, E>