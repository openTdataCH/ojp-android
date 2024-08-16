package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.dto.response.tir.trips.TripDto
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import kotlinx.coroutines.isActive
import java.time.LocalDateTime
import java.util.concurrent.CancellationException
import kotlin.coroutines.coroutineContext

/**
 * Created by Michael Ruppen on 27.06.2024
 */
internal class RequestTrips(
    private val ojpRepository: OjpRepository
) {

    private var state = TripRequestState()

    suspend operator fun invoke(
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
    ): Result<TripDeliveryDto> {
        // do not return or overwrite state, if user canceled the request (long running task or something)
        if (!coroutineContext.isActive) return Result.Error(OjpError.RequestCancelled(CancellationException()))

        state = state.copy(
            origin = origin,
            destination = destination,
            via = via,
            time = time,
            isSearchForDepartureTime = isSearchForDepartureTime,
            params = params,
        )

        return when (val response =
            ojpRepository.requestTrips(
                origin = origin,
                destination = destination,
                via = via,
                time = time,
                isSearchForDepartureTime = isSearchForDepartureTime,
                params = params
            )
        ) {
            is Result.Success -> {
                val delivery = response.data
                val filteredTrips = delivery.tripResults?.let { filterDuplicatedTrips(it) }
                Result.Success(delivery.copy(tripResults = filteredTrips))
            }

            is Result.Error -> Result.Error(response.error)
        }
    }

    suspend fun loadPrevious(numberOfResultsBefore: Int): Result<TripDeliveryDto> {
        if (state.origin == null || state.minDateTime == null) return Result.Error(OjpError.Unknown(Exception("Request trips first")))

        return invoke(
            origin = state.origin!!,
            destination = state.destination!!,
            via = state.via,
            time = state.minDateTime!!,
            isSearchForDepartureTime = true,
            params = state.params?.copy(
                numberOfResultsBefore = numberOfResultsBefore,
                numberOfResultsAfter = null,
                numberOfResults = null
            ),
        )
    }

    suspend fun loadNext(numberOfResultsAfter: Int): Result<TripDeliveryDto> {
        if (state.origin == null || state.maxDateTime == null) return Result.Error(OjpError.Unknown(Exception("Request trips first")))

        return invoke(
            origin = state.origin!!,
            destination = state.destination!!,
            via = state.via,
            time = state.maxDateTime!!,
            isSearchForDepartureTime = true,
            params = state.params?.copy(
                numberOfResultsBefore = null,
                numberOfResultsAfter = numberOfResultsAfter,
                numberOfResults = null
            ),
        )
    }

    fun reset() {
        state = TripRequestState()
    }

    private fun filterDuplicatedTrips(tripResults: List<TripResultDto>): List<TripResultDto> {
        return tripResults
            .filter { it.trip is TripDto } // tripSummary not yet supported
            .mapNotNull { tripResult ->
                val trip = tripResult.trip as TripDto
                val hash = trip.hashCode()

                when (state.existingHashes.contains(hash)) {
                    false -> {
                        state.existingHashes.add(hash)
                        if (state.maxDateTime == null || trip.startTime > state.maxDateTime) {
                            state = state.copy(maxDateTime = trip.startTime)
                        }
                        if (state.minDateTime == null || trip.startTime < state.minDateTime) {
                            state = state.copy(minDateTime = trip.startTime)
                        }
                        tripResult
                    }

                    true -> null
                }
            }
    }

    data class TripRequestState(
        val origin: PlaceReferenceDto? = null,
        val destination: PlaceReferenceDto? = null,
        val via: PlaceReferenceDto? = null,
        val time: LocalDateTime? = null,
        val isSearchForDepartureTime: Boolean = true,
        val params: TripParamsDto? = null,
        val minDateTime: LocalDateTime? = null,
        val maxDateTime: LocalDateTime? = null,
        val existingHashes: MutableList<Int> = mutableListOf()
    )
}