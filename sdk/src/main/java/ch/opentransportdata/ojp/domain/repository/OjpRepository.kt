package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
import java.io.InputStream
import java.time.LocalDateTime

internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(
        languageCode: LanguageCode,
        term: String,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>>

    suspend fun placeResultsFromCoordinates(
        languageCode: LanguageCode,
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>>

    suspend fun requestTrips(
        languageCode: LanguageCode,
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParams?
    ): Result<TripDeliveryDto>

    suspend fun requestMockTrips(
        stream: InputStream
    ): Result<TripDeliveryDto>
}