package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.data.dto.converter.PlaceTypeRestrictionConverter
import ch.opentransportdata.ojp.data.dto.request.OjpRequestDto
import ch.opentransportdata.ojp.data.dto.request.ServiceRequestDto
import ch.opentransportdata.ojp.data.dto.request.lir.*
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.usecase.Initializer
import ch.opentransportdata.ojp.utils.GeoLocationUtil.initWithGeoLocationAndBoxSize
import ch.opentransportdata.ojp.utils.toInstantString
import com.tickaroo.tikxml.TypeAdapterNotFoundException
import com.tickaroo.tikxml.TypeConverterNotFoundException
import com.tickaroo.tikxml.XmlDataException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.LocalDateTime
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RemoteOjpDataSourceImpl(
    private val ojpService: OjpService, private val initializer: Initializer
) : RemoteOjpDataSource {

    private val numberOfResults = 10

    private val url: String
        get() = initializer.baseUrl + initializer.endpoint

    override suspend fun searchLocationBySearchTerm(
        term: String, restrictions: List<PlaceTypeRestriction>
    ): Result<OjpDto, OjpError> = withContext(Dispatchers.IO) {
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

        return@withContext handleLocationInformationRequest(request)
    }

    override suspend fun searchLocationByCoordinates(
        longitude: Double, latitude: Double, restrictions: List<PlaceTypeRestriction>
    ): Result<OjpDto, OjpError> = withContext(Dispatchers.IO) {
        val requestTime = LocalDateTime.now()

        val request = OjpDto(
            ojpRequest = OjpRequestDto(
                serviceRequest = ServiceRequestDto(
                    requestTimestamp = requestTime.toInstantString(),
                    requestorRef = initializer.requesterReference,
                    locationInformationRequest = LocationInformationRequestDto(
                        requestTimestamp = requestTime.toInstantString(), initialInput = InitialInputDto(
                            geoRestriction = GeoRestrictionDto(
                                rectangle = initWithGeoLocationAndBoxSize(longitude, latitude)
                            )
                        ), restrictions = RestrictionsDto(
                            types = restrictions.map { RestrictionType(PlaceTypeRestrictionConverter().write(it)) },
                            numberOfResults = numberOfResults,
                            ptModeIncluded = true
                        )
                    )
                )
            )
        )

        return@withContext handleLocationInformationRequest(request)
    }

    private suspend fun handleLocationInformationRequest(request: OjpDto): Result<OjpDto, OjpError> {
        return try {
            Timber.d("Request object: $request")
            val response = ojpService.locationInformationRequest(url, request)
            Result.Success(response)
        } catch (e: HttpException) {
            Timber.e("Http Exception with error code: ${e.code()}")
            Result.Error(OjpError.UNEXPECTED_HTTP_STATUS)
        } catch (e: TypeConverterNotFoundException) {
            Timber.e(e, "Missing TypeConverter")
            Result.Error(OjpError.ENCODING_FAILED)
        } catch (e: TypeAdapterNotFoundException) {
            Timber.e(e, "Missing TypeAdapter")
            Result.Error(OjpError.ENCODING_FAILED)
        } catch (e: XmlDataException) {
            Timber.e(e, "Error in XML Data")
            Result.Error(OjpError.DECODING_FAILED)
        } catch (e: NullPointerException) {
            Timber.e(e, "A required element is missing")
            Result.Error(OjpError.MISSING_ELEMENT)
        } catch (e: Exception) {
            Timber.e(e, "Error creating request or receiving response")
            Result.Error(OjpError.UNKNOWN)
        }
    }
}