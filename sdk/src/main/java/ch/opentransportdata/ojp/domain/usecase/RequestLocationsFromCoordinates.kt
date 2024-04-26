package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Response
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import ch.opentransportdata.ojp.utils.GeoLocationUtil


/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RequestLocationsFromCoordinates(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Response<List<PlaceResultDto>> {
        return when (val response =
            ojpRepository.placeResultsFromCoordinates(longitude = longitude, latitude = latitude, restrictions = restrictions)) {
            is Response.Success -> {
                val sortedList = sortByDistance(longitude, latitude, response.data)
                Response.Success(sortedList)
            }

            is Response.Error -> response //if needed, map the error to a predefined List of Errors
        }
    }

    private fun sortByDistance(
        originLongitude: Double,
        originLatitude: Double,
        result: List<PlaceResultDto>
    ): List<PlaceResultDto> {

        result.forEach {
            it.distance = GeoLocationUtil.distanceBetweenGeoPoints(
                originLatitude,
                originLongitude,
                it.place.position?.latitude,
                it.place.position?.longitude
            )
        }

        return result.sortedBy { it.distance }
    }

}