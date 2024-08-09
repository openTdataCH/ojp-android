package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import java.time.LocalDateTime

internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(
        term: String,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>>

    suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>>

    suspend fun requestTrips(
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
    ): Result<TripDeliveryDto>
}