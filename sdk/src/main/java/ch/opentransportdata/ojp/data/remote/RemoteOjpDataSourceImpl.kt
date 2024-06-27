package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.PlaceTypeRestrictionConverter
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.lir.*
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.utils.GeoLocationUtil.initWithGeoLocationAndBoxSize
import ch.opentransportdata.ojp.utils.toInstantString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RemoteOjpDataSourceImpl(
    private val ojpService: OjpService,
    private val initializer: Initializer
) : RemoteOjpDataSource {

    private val numberOfResults = 10
    private val placeTypeRestrictionConverter = PlaceTypeRestrictionConverter()
    private val url: String
        get() = initializer.baseUrl + initializer.endpoint

    override suspend fun searchLocationBySearchTerm(
        term: String, restrictions: List<PlaceTypeRestriction>
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val request = createRequest(
            requestTime = requestTime,
            locationInformationRequest = LocationInformationRequestDto(
                requestTimestamp = requestTime.toInstantString(),
                initialInput = InitialInputDto(name = term),
                restrictions = createRestrictions(restrictions)
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    override suspend fun searchLocationByCoordinates(
        longitude: Double, latitude: Double, restrictions: List<PlaceTypeRestriction>
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val request = createRequest(
            requestTime = requestTime,
            locationInformationRequest = LocationInformationRequestDto(
                requestTimestamp = requestTime.toInstantString(),
                initialInput = InitialInputDto(
                    geoRestriction = GeoRestrictionDto(
                        rectangle = initWithGeoLocationAndBoxSize(longitude, latitude)
                    )
                ), restrictions = createRestrictions(restrictions)
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    private fun createRequest(requestTime: LocalDateTime, locationInformationRequest: LocationInformationRequestDto): OjpDto {
        return OjpDto(
            ojpRequest = OjpRequestDto(
                serviceRequest = ServiceRequestDto(
                    requestTimestamp = requestTime.toInstantString(),
                    requestorRef = initializer.requesterReference,
                    locationInformationRequest = locationInformationRequest
                )
            )
        )
    }

    private fun createRestrictions(restrictions: List<PlaceTypeRestriction>): RestrictionsDto {
        return RestrictionsDto(
            types = restrictions.map { RestrictionType(placeTypeRestrictionConverter.write(it)) },
            numberOfResults = numberOfResults,
            ptModeIncluded = true
        )
    }
}