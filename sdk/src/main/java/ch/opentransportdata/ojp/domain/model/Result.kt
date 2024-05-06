package ch.opentransportdata.ojp.domain.model

import ch.opentransportdata.ojp.domain.model.error.Error

sealed interface Result<out D, out E : Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : ch.opentransportdata.ojp.domain.model.error.Error>(val error: E) : Result<Nothing, E>
}

typealias EmptyDataResult<E> = Result<Unit, E>