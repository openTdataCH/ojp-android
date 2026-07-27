package ch.opentransportdata.ojp.data.remote.location

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestContextDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.lir.*
import ch.opentransportdata.ojp.data.dto.request.ser.ModeFilterDto
import ch.opentransportdata.ojp.data.dto.response.place.OsmTagDto
import ch.opentransportdata.ojp.data.dto.response.place.PointOfInterestCategoryDto
import ch.opentransportdata.ojp.data.remote.OjpService
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.shortName
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.utils.GeoLocationUtil.initWithGeoLocationAndBoxSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RemoteLocationInformationDataSourceImpl(
    private val ojpService: OjpService,
    private val initializer: Initializer
) : RemoteLocationInformationDataSource {

    private val url: String
        get() = initializer.baseUrl + initializer.endpoint

    override suspend fun searchLocationBySearchTerm(
        languageCode: LanguageCode,
        term: String,
        restrictions: LocationInformationParams
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val request = createRequest(
            languageCode = languageCode,
            requestTime = requestTime,
            locationInformationRequest = LocationInformationRequestDto(
                requestTimestamp = requestTime,
                initialInput = InitialInputDto(name = term),
                restrictions = createRestrictions(restrictions)
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    override suspend fun searchLocationByCoordinates(
        languageCode: LanguageCode,
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val request = createRequest(
            languageCode = languageCode,
            requestTime = requestTime,
            locationInformationRequest = LocationInformationRequestDto(
                requestTimestamp = requestTime,
                initialInput = InitialInputDto(
                    geoRestriction = GeoRestrictionDto(
                        rectangle = initWithGeoLocationAndBoxSize(longitude, latitude)
                    )
                ),
                restrictions = createRestrictions(restrictions)
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    override suspend fun searchLocationByRectangle(
        languageCode: LanguageCode,
        upperLeftLongitude: Double,
        upperLeftLatitude: Double,
        lowerRightLongitude: Double,
        lowerRightLatitude: Double,
        restrictions: LocationInformationParams
    ): OjpDto = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val request = createRequest(
            languageCode = languageCode,
            requestTime = requestTime,
            locationInformationRequest = LocationInformationRequestDto(
                requestTimestamp = requestTime,
                initialInput = InitialInputDto(
                    geoRestriction = GeoRestrictionDto(
                        rectangle = RectangleDto(
                            upperLeft = PointDto(longitude = upperLeftLongitude, latitude = upperLeftLatitude),
                            lowerRight = PointDto(longitude = lowerRightLongitude, latitude = lowerRightLatitude)
                        )
                    )
                ),
                restrictions = createRestrictions(restrictions)
            )
        )

        return@withContext ojpService.serviceRequest(url, request)
    }

    private fun createRequest(
        languageCode: LanguageCode,
        requestTime: LocalDateTime,
        locationInformationRequest: LocationInformationRequestDto
    ): OjpDto {
        return OjpDto(
            ojpRequest = OjpRequestDto(
                serviceRequest = ServiceRequestDto(
                    serviceRequestContext = ServiceRequestContextDto(
                        language = languageCode.shortName
                    ),
                    requestTimestamp = requestTime,
                    requestorRef = initializer.requesterReference,
                    locationInformationRequest = locationInformationRequest
                )
            )
        )
    }

    private fun createRestrictions(restrictions: LocationInformationParams): RestrictionsDto {
        return RestrictionsDto(
            types = restrictions.types,
            modeFilter = restrictions.modeFilter?.let { filter ->
                ModeFilterDto(
                    ptMode = filter.ptMode,
                    personalModes = filter.personalModes,
                    exclude = filter.exclude
                )
            },
            pointOfInterestFilter = restrictions.pointOfInterestFilter?.let { filter ->
                PointOfInterestFilterDto(
                    pointOfInterestCategory = filter.categories.map { category ->
                        PointOfInterestCategoryDto(
                            osmTag = category.osmTag?.let { OsmTagDto(tag = it.tag, value = it.value) },
                            pointOfInterestClassification = category.classification
                        )
                    }.ifEmpty { null },
                    exclude = filter.exclude
                )
            },
            numberOfResults = restrictions.numberOfResults,
            ptModeIncluded = restrictions.ptModeIncluded
        )
    }
}