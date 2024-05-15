package ch.opentransportdata.ojp.data.repository

import ch.opentransportdata.ojp.data.dto.response.delivery.LocationInformationDeliveryDto
import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.data.remote.RemoteOjpDataSource
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Response
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import timber.log.Timber

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class OjpRepositoryImpl(
    private val remoteDataSource: RemoteOjpDataSource,
) : OjpRepository {

    override suspend fun placeResultsFromSearchTerm(
        term: String,
        restrictions: List<PlaceTypeRestriction>
    ): Response<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationBySearchTerm(term, restrictions).ojpResponse
            val result = response?.serviceDelivery?.ojpDelivery as? LocationInformationDeliveryDto
            val lirList = result?.placeResults ?: emptyList()
            Response.Success(lirList)
        } catch (e: Exception) {
            //TODO: Implement errors
            Timber.e(e, "Error creating request or receiving response")
            Response.Error(IllegalStateException("Request did not work", e))
        }
    }

    override suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Response<List<PlaceResultDto>> {
        return try {
            val response = remoteDataSource.searchLocationByCoordinates(longitude, latitude, restrictions).ojpResponse
            val result = response?.serviceDelivery?.ojpDelivery as? LocationInformationDeliveryDto
            val lirList = result?.placeResults ?: emptyList()
            Response.Success(lirList)
        } catch (e: Exception) {
            //TODO: Implement errors
            Timber.e(e, "Error creating request or receiving response")
            Response.Error(IllegalStateException("Request did not work", e))
        }
    }
}