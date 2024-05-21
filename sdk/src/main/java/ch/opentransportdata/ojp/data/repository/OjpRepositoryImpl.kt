package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.Error
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import com.tickaroo.tikxml.TypeAdapterNotFoundException
import com.tickaroo.tikxml.TypeConverterNotFoundException
import com.tickaroo.tikxml.XmlDataException
import retrofit2.HttpException
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpRepositoryImpl(
    private val remoteDataSource: RemoteOjpDataSource,
) : OjpRepository {

    override suspend fun placeResultsFromSearchTerm(
        term: String, restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationBySearchTerm(term, restrictions).ojpResponse
            val delivery = response?.serviceDelivery?.ojpDelivery as? LocationInformationDeliveryDto
            val result = delivery?.placeResults ?: emptyList()
            Result.Success(result)
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }

    override suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationByCoordinates(longitude, latitude, restrictions).ojpResponse
            val delivery = response?.serviceDelivery?.ojpDelivery as? LocationInformationDeliveryDto
            val result = delivery?.placeResults ?: emptyList()
            Result.Success(result)
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }

    private fun handleError(exception: Exception): Error {
        return when (exception) {
            is HttpException -> {
                Timber.e("Http Exception with error code: ${exception.code()}")
                OjpError.UNEXPECTED_HTTP_STATUS
            }

            is TypeConverterNotFoundException -> {
                Timber.e(exception, "Missing TypeConverter")
                OjpError.ENCODING_FAILED
            }

            is TypeAdapterNotFoundException -> {
                Timber.e(exception, "Missing TypeAdapter")
                OjpError.ENCODING_FAILED
            }

            is XmlDataException -> {
                Timber.e(exception, "Error in XML Data")
                OjpError.DECODING_FAILED
            }

            is NullPointerException -> {
                Timber.e(exception, "A required element is missing")
                OjpError.MISSING_ELEMENT
            }

            else -> {
                Timber.e(exception, "Error creating request or receiving response")
                OjpError.UNKNOWN
            }
        }
    }
}