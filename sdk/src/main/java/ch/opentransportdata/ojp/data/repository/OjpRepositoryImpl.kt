package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpRepositoryImpl(
    private val remoteDataSource: RemoteOjpDataSource,
) : OjpRepository {

    override suspend fun placeResultsFromSearchTerm(
        term: String, restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>, OjpError> {
        return try {
            val response = remoteDataSource.searchLocationBySearchTerm(term, restrictions).ojpResponse
            val result = response?.serviceDelivery?.locationInformation?.placeResults ?: emptyList()
            Result.Success(result)
        } catch (e: Exception) {
            Timber.e(e, "Error creating request or receiving response")
            return Result.Error(OjpError.UNKNOWN)
        }
    }

    override suspend fun placeResultsFromCoordinates(
        longitude: Double, latitude: Double, restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>, OjpError> {
        return try {
            val response = remoteDataSource.searchLocationByCoordinates(longitude, latitude, restrictions).ojpResponse
            val result = response?.serviceDelivery?.locationInformation?.placeResults ?: emptyList()
            Result.Success(result)
        } catch (e: Exception) {
            Timber.e(e, "Error creating request or receiving response")
            return Result.Error(OjpError.UNKNOWN)
        }
    }
}