package ch.opentransportdata.ojp.data.remote.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.tir.TripInfoParamsDto
import ch.opentransportdata.ojp.data.dto.request.tr.IndividualTransportOptionDto
import ch.opentransportdata.ojp.data.dto.request.tr.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 27.06.2024
 */
internal interface RemoteTripDataSource {

    suspend fun requestTrips(
        languageCode: LanguageCode,
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto? = null,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParams?,
        individualTransportOption: IndividualTransportOptionDto?
    ): OjpDto

    suspend fun requestTripRefinement(
        languageCode: LanguageCode,
        tripResultDto: TripResultDto,
        params: TripRefineParam?
    ): OjpDto

    suspend fun requestTripInfo(
        languageCode: LanguageCode,
        tripResultDto: TripResultDto,
        params: TripInfoParamsDto?
    ): OjpDto
}