package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.utils.toInstantString
import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.lir.GeoRestrictionDto
import ch.opentransportdata.ojp.data.dto.request.lir.InitialInputDto
import ch.opentransportdata.ojp.data.dto.request.lir.LocationInformationRequestDto
import ch.opentransportdata.ojp.data.dto.request.lir.RestrictionsDto
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.utils.GeoLocationUtil.initWithGeoLocationAndBoxSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
class RemoteOjpDataSourceImpl(
    private val ojpService: OjpService,
    private val initializer: Initializer
) : RemoteOjpDataSource {

    private val numberOfResults = 10

    private val url: String
        get() = initializer.baseUrl + initializer.endpoint


    override suspend fun searchLocationBySearchTerm(term: String, onlyStation: Boolean): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()
        val restrictionType = if (onlyStation) "stop" else "-"

        val request = OjpDto(
            ojpRequest = OjpRequestDto(
                serviceRequest = ServiceRequestDto(
                    requestTimestamp = requestTime.toInstantString(),
                    requestorRef = initializer.requestorReference,
                    locationInformationRequest = LocationInformationRequestDto(
                        requestTimestamp = requestTime.toInstantString(),
                        initialInput = InitialInputDto(name = term),
                        restrictions = RestrictionsDto(
                            type = restrictionType,
                            numberOfResults = numberOfResults,
                            ptModeIncluded = true
                        )
                    )
                )
            )
        )

        return@withContext ojpService.locationInformationRequest(url, request)
    }

    override suspend fun searchLocationByCoordinates(longitude: Double, latitude: Double, onlyStation: Boolean): OjpDto =
        withContext(Dispatchers.IO) {
            val requestTime = LocalDateTime.now()
            val restrictionType = if (onlyStation) "stop" else "-"

            val request = OjpDto(
                ojpRequest = OjpRequestDto(
                    serviceRequest = ServiceRequestDto(
                        requestTimestamp = requestTime.toInstantString(),
                        requestorRef = initializer.requestorReference,
                        locationInformationRequest = LocationInformationRequestDto(
                            requestTimestamp = requestTime.toInstantString(),
                            initialInput = InitialInputDto(
                                geoRestriction = GeoRestrictionDto(
                                    rectangle = initWithGeoLocationAndBoxSize(longitude, latitude)
                                )
                            ),
                            restrictions = RestrictionsDto(
                                type = restrictionType,
                                numberOfResults = numberOfResults,
                                ptModeIncluded = true
                            )
                        )
                    )
                )
            )

            return@withContext ojpService.locationInformationRequest(url, request)
        }
}