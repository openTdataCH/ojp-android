package ch.opentransportdata.ojp.data.remote.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.tir.*
import ch.opentransportdata.ojp.data.dto.response.NameDto
import ch.opentransportdata.ojp.data.remote.OjpService
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
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto?,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
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
            requestTime = requestTime,
            tripRequest = TripRequestDto(
                requestTimestamp = requestTime,
                origin = originPlace,
                destination = destinationPlace,
                via = vias ?: emptyList(),
                params = params
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    private fun createRequest(requestTime: LocalDateTime, tripRequest: TripRequestDto): OjpDto {
        return OjpDto(
            ojpRequest = OjpRequestDto(
                serviceRequest = ServiceRequestDto(
                    requestTimestamp = requestTime,
                    requestorRef = initializer.requesterReference,
                    tripRequest = tripRequest
                )
            )
        )
    }

}