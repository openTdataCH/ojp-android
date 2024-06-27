package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.request.tir.TripParamsDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.data.remote.tir.RemoteTirDataSource
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import com.tickaroo.tikxml.TypeAdapterNotFoundException
import com.tickaroo.tikxml.TypeConverterNotFoundException
import com.tickaroo.tikxml.XmlDataException
import kotlinx.coroutines.CancellationException
import org.joda.time.LocalDateTime
import retrofit2.HttpException
import timber.log.Timber
import java.time.Instant

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpRepositoryImpl(
    private val remoteDataSource: RemoteOjpDataSource,
    private val tripDataSource: RemoteTirDataSource
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

    override suspend fun requestTrips(
        origin: PlaceResultDto,
        destination: PlaceResultDto,
        via: PlaceResultDto?,
        time: Instant,
        params: TripParamsDto?
    ) {
        //todo: add return value
        try {
            val response = tripDataSource.requestTrips(origin, destination, via, LocalDateTime(time.toEpochMilli()), params)
            Result.Success(response)
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }


    private fun handleError(exception: Exception): OjpError {
        return when (exception) {
            is HttpException -> {
                Timber.e("Http Exception with error code: ${exception.code()}")
                OjpError.UnexpectedHttpStatus(exception)
            }

            is TypeConverterNotFoundException -> {
                Timber.e(exception, "Missing TypeConverter")
                OjpError.EncodingFailed(exception)
            }

            is TypeAdapterNotFoundException -> {
                Timber.e(exception, "Missing TypeAdapter")
                OjpError.EncodingFailed(exception)
            }

            is XmlDataException -> {
                Timber.e(exception, "Error in XML Data")
                OjpError.DecodingFailed(exception)
            }

            is NullPointerException -> {
                Timber.e(exception, "A required element is missing")
                OjpError.MissingElement(exception)
            }

            is CancellationException -> {
                Timber.e(exception, "Coroutine is cancelled")
                OjpError.RequestCancelled(exception)
            }

            else -> {
                Timber.e(exception, "Error creating request or receiving response")
                OjpError.Unknown(exception)
            }
        }
    }
}