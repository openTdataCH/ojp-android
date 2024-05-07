package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError
import ch.opentransportdata.ojp.domain.repository.OjpRepository

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpRepositoryImpl(
    private val remoteDataSource: RemoteOjpDataSource,
) : OjpRepository {

    override suspend fun placeResultsFromSearchTerm(
        term: String, restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>, OjpError> {
        return when (val response = remoteDataSource.searchLocationBySearchTerm(term, restrictions)) {
            is Result.Success -> {
                val result = response.data.ojpResponse?.serviceDelivery?.locationInformation?.placeResults ?: emptyList()
                Result.Success(result)
            }

            is Result.Error -> Result.Error(response.error)
        }
    }

    override suspend fun placeResultsFromCoordinates(
        longitude: Double, latitude: Double, restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>, OjpError> {
        return when (val response = remoteDataSource.searchLocationByCoordinates(longitude, latitude, restrictions)) {
            is Result.Success -> {
                val result = response.data.ojpResponse?.serviceDelivery?.locationInformation?.placeResults ?: emptyList()
                Result.Success(result)
            }

            is Result.Error -> Result.Error(response.error)
        }
    }
}