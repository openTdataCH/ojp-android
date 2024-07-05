package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import java.time.LocalDateTime

internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(
        term: String,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>>

    suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>>

    suspend fun requestTrips(
        origin: PlaceResultDto,
        destination: PlaceResultDto,
        via: PlaceResultDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
    ): Result<TripDeliveryDto>
}