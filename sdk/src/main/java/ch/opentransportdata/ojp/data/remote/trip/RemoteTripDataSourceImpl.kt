package ch.opentransportdata.ojp.data.remote.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.PtModeTypeConverter
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestContextDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.tir.*
import ch.opentransportdata.ojp.data.remote.OjpService
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.shortName
import ch.opentransportdata.ojp.domain.usecase.Initializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 27.06.2024
 */
internal class RemoteTripDataSourceImpl(
    private val ojpService: OjpService,
    private val initializer: Initializer
) : RemoteTripDataSource {

    private val url: String
        get() = initializer.baseUrl + initializer.endpoint


    override suspend fun requestTrips(
        languageCode: LanguageCode,
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto?,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParams?
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()
        val originPlace = PlaceContextDto(
            placeReference = origin,
            departureArrivalTime = if (isSearchForDepartureTime) time else null
        )

        val destinationPlace = PlaceContextDto(
            placeReference = destination,
            departureArrivalTime = if (isSearchForDepartureTime) null else time
        )

        val vias = via?.let {
            listOf(TripVia(viaPoint = it))
        }

        val request = createRequest(
            languageCode = languageCode,
            requestTime = requestTime,
            tripRequest = TripRequestDto(
                requestTimestamp = requestTime,
                origin = originPlace,
                destination = destinationPlace,
                via = vias ?: emptyList(),
                params = params?.mapToBackendParams()
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    private fun createRequest(languageCode: LanguageCode, requestTime: LocalDateTime, tripRequest: TripRequestDto): OjpDto {
        return OjpDto(
            ojpRequest = OjpRequestDto(
                serviceRequest = ServiceRequestDto(
                    serviceRequestContext = ServiceRequestContextDto(
                        language = languageCode.shortName
                    ),
                    requestTimestamp = requestTime,
                    requestorRef = initializer.requesterReference,
                    tripRequest = tripRequest
                )
            )
        )
    }

    private fun TripParams.mapToBackendParams(): TripParamsDto {
        return TripParamsDto(
            numberOfResults = this.numberOfResults,
            numberOfResultsBefore = this.numberOfResultsBefore,
            numberOfResultsAfter = this.numberOfResultsAfter,
            includeTrackSections = if (this.includeTrackSections) true else null,
            includeLegProjection = if (this.includeLegProjection) true else null,
            includeTurnDescription = if (this.includeTurnDescription) true else null,
            includeIntermediateStops = if (this.includeIntermediateStops) true else null,
            includeAllRestrictedLines = if (this.includeAllRestrictedLines) true else null,
            useRealtimeData = this.useRealtimeData,
            modeAndModeOfOperationFilter = this.modeAndModeOfOperationFilter?.map { filter ->
                ModeAndModeOfOperationFilterDto(
                    ptMode = filter.ptMode.map { PtModeType(PtModeTypeConverter().write(it)) },
                    exclude = filter.exclude
                )
            },
        )
    }

}