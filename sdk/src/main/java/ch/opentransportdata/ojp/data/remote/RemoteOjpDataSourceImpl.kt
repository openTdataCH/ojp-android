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
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RemoteOjpDataSourceImpl(
    private val ojpService: OjpService,
    private val initializer: Initializer
) : RemoteOjpDataSource {

    private val numberOfResults = 10

    private val url: String
        get() = initializer.baseUrl + initializer.endpoint

    override suspend fun searchLocationBySearchTerm(term: String, restrictions: List<PlaceTypeRestriction>): OjpDto =
        withContext(Dispatchers.IO) {
            val requestTime = LocalDateTime.now()

            val request = OjpDto(
                ojpRequest = OjpRequestDto(
                    serviceRequest = ServiceRequestDto(
                        requestTimestamp = requestTime.toInstantString(),
                        requestorRef = initializer.requesterReference,
                        locationInformationRequest = LocationInformationRequestDto(
                            requestTimestamp = requestTime.toInstantString(),
                            initialInput = InitialInputDto(name = term),
                            restrictions = RestrictionsDto(
                                types = restrictions.map { RestrictionType(PlaceTypeRestrictionConverter().write(it)) },
                                numberOfResults = numberOfResults,
                                ptModeIncluded = true
                            )
                        )
                    )
                )
            )

            Timber.d("Request object: $request")
            return@withContext ojpService.locationInformationRequest(url, request)
        }

    override suspend fun searchLocationByCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): OjpDto =
        withContext(Dispatchers.IO) {
            val requestTime = LocalDateTime.now()

            val request = OjpDto(
                ojpRequest = OjpRequestDto(
                    serviceRequest = ServiceRequestDto(
                        requestTimestamp = requestTime.toInstantString(),
                        requestorRef = initializer.requesterReference,
                        locationInformationRequest = LocationInformationRequestDto(
                            requestTimestamp = requestTime.toInstantString(),
                            initialInput = InitialInputDto(
                                geoRestriction = GeoRestrictionDto(
                                    rectangle = initWithGeoLocationAndBoxSize(longitude, latitude)
                                )
                            ),
                            restrictions = RestrictionsDto(
                                types = restrictions.map { RestrictionType(PlaceTypeRestrictionConverter().write(it)) },
                                numberOfResults = numberOfResults,
                                ptModeIncluded = true
                            )
                        )
                    )
                )
            )

            Timber.d("Request object: $request")
            return@withContext ojpService.locationInformationRequest(url, request)
        }
}