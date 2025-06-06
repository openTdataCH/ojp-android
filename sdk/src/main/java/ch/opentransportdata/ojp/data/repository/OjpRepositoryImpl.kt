package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.request.tir.PlaceReferenceDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.delivery.TripRefineDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.tir.TripResultDto
import ch.opentransportdata.ojp.data.local.trip.LocalTripDataSource
import ch.opentransportdata.ojp.data.remote.location.RemoteLocationInformationDataSource
import ch.opentransportdata.ojp.data.remote.trip.RemoteTripDataSource
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.TripParams
import ch.opentransportdata.ojp.domain.model.TripRefineParam
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import com.tickaroo.tikxml.TypeAdapterNotFoundException
import com.tickaroo.tikxml.TypeConverterNotFoundException
import com.tickaroo.tikxml.XmlDataException
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import timber.log.Timber
import java.io.InputStream
import java.time.LocalDateTime

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpRepositoryImpl(
    private val remoteDataSource: RemoteLocationInformationDataSource,
    private val tripDataSource: RemoteTripDataSource,
    private val localTripDataSource: LocalTripDataSource
) : OjpRepository {

    override suspend fun placeResultsFromSearchTerm(
        languageCode: LanguageCode,
        term: String,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationBySearchTerm(languageCode, term, restrictions).ojpResponse
            val delivery = response?.serviceDelivery?.ojpDelivery as? LocationInformationDeliveryDto
            val result = delivery?.placeResults ?: emptyList()
            Result.Success(result)
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }

    override suspend fun placeResultsFromCoordinates(
        languageCode: LanguageCode,
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return try {
            val response =
                remoteDataSource.searchLocationByCoordinates(languageCode, longitude, latitude, restrictions).ojpResponse
            val delivery = response?.serviceDelivery?.ojpDelivery as? LocationInformationDeliveryDto
            val result = delivery?.placeResults ?: emptyList()
            Result.Success(result)
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }

    override suspend fun requestTrips(
        languageCode: LanguageCode,
        origin: PlaceReferenceDto,
        destination: PlaceReferenceDto,
        via: PlaceReferenceDto?,
        time: LocalDateTime,
        isSearchForDepartureTime: Boolean,
        params: TripParams?
    ): Result<TripDeliveryDto> {
        return try {
            val response = tripDataSource.requestTrips(
                languageCode = languageCode,
                origin = origin,
                destination = destination,
                via = via,
                time = time,
                isSearchForDepartureTime = isSearchForDepartureTime,
                params = params
            )
            val delivery = response.ojpResponse?.serviceDelivery?.ojpDelivery as? TripDeliveryDto
            if (delivery != null) Result.Success(delivery) else Result.Error(OjpError.Unknown(Exception("Trip delivery is null")))
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }

    override suspend fun requestMockTrips(stream: InputStream): Result<TripDeliveryDto> {
        return try {
            val response = localTripDataSource.requestMockTrips(stream)
            val delivery = response.ojpResponse?.serviceDelivery?.ojpDelivery as? TripDeliveryDto
            if (delivery != null) Result.Success(delivery) else Result.Error(OjpError.Unknown(Exception("Trip delivery is null")))
        } catch (exception: Exception) {
            val error = handleError(exception)
            Result.Error(error)
        }
    }

    override suspend fun requestTripRefinement(
        languageCode: LanguageCode,
        tripResultDto: TripResultDto,
        params: TripRefineParam
    ): Result<TripRefineDeliveryDto> {
        return try {
            val response = tripDataSource.requestTripRefinement(
                languageCode = languageCode,
                tripResultDto = tripResultDto,
                params = params
            )
            val delivery = response.ojpResponse?.serviceDelivery?.ojpDelivery as? TripRefineDeliveryDto
            if (delivery != null) Result.Success(delivery) else Result.Error(OjpError.Unknown(Exception("Trip delivery is null")))
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