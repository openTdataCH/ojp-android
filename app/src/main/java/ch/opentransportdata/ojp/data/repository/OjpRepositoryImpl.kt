package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.domain.model.Response
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
class OjpRepositoryImpl(
    private val remoteDataSource: RemoteOjpDataSource,
) : OjpRepository {

    override suspend fun placeResultsFromSearchTerm(term: String, onlyStation: Boolean): Response<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationBySearchTerm(term, onlyStation).ojpResponse
            val result = response?.serviceDelivery?.locationInformation?.placeResults ?: emptyList()
            Response.Success(result)
        } catch (e: Exception) {
            //TODO: Implement errors
            Timber.e(e, "Error creating request or receiving response")
            Response.Error(IllegalStateException("Request did not work", e))
        }
    }

    override suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        onlyStation: Boolean
    ): Response<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationByCoordinates(longitude, latitude, onlyStation).ojpResponse
            val result = response?.serviceDelivery?.locationInformation?.placeResults ?: emptyList()
            Response.Success(result)
        } catch (e: Exception) {
            //TODO: Implement errors
            Timber.e(e, "Error creating request or receiving response")
            Response.Error(IllegalStateException("Request did not work", e))
        }
    }
}