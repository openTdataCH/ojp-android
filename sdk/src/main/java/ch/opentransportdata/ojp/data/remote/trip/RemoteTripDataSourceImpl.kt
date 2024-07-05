package ch.opentransportdata.ojp.data.remote.trip

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.tir.*
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.place.AddressDto
import ch.opentransportdata.ojp.data.dto.response.place.StopPlaceDto
import ch.opentransportdata.ojp.data.remote.OjpService
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.utils.toInstantString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime

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
        origin: PlaceResultDto,
        destination: PlaceResultDto,
        via: PlaceResultDto?,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParamsDto?
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val originName = when (origin.place.placeType) {
            is StopPlaceDto -> origin.place.placeType.name
            is AddressDto -> origin.place.placeType.name
            else -> null
        }

        val originPlace = PlaceContextDto(
            placeReference = PlaceReferenceDto(
                ref = (origin.place.placeType as? StopPlaceDto)?.stopPlaceRef,
                stationName = originName,
                position = origin.place.position
            ),
            departureArrivalTime = if (isSearchForDepartureTime) time.toInstantString() else null
        )

        val destinationName = when (destination.place.placeType) {
            is StopPlaceDto -> destination.place.placeType.name
            is AddressDto -> destination.place.placeType.name
            else -> null
        }

        val destinationPlace = PlaceContextDto(
            placeReference = PlaceReferenceDto(
                ref = (destination.place.placeType as? StopPlaceDto)?.stopPlaceRef,
                stationName = destinationName,
                position = destination.place.position
            ),
            departureArrivalTime = if (isSearchForDepartureTime) null else time.toInstantString()
        )

        val vias = via?.let {
            val viaName = when (destination.place.placeType) {
                is StopPlaceDto -> destination.place.placeType.name
                is AddressDto -> destination.place.placeType.name
                else -> null
            }

            listOf(
                TripVia(
                    viaPoint = PlaceReferenceDto(
                        ref = (destination.place.placeType as? StopPlaceDto)?.stopPlaceRef,
                        stationName = viaName,
                        position = destination.place.position
                    )
                )
            )
        }

        val request = createRequest(
            requestTime = requestTime,
            tripRequest = TripRequestDto(
                requestTimestamp = requestTime.toInstantString(),
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
                    requestTimestamp = requestTime.toInstantString(),
                    requestorRef = initializer.requesterReference,
                    tripRequest = tripRequest
                )
            )
        )
    }

}