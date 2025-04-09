package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.repository.OjpRepository
import ch.opentransportdata.ojp.utils.GeoLocationUtil


/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RequestLocationsFromCoordinates(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(
        languageCode: LanguageCode,
        longitude: Double,
        latitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return when (val response =
            ojpRepository.placeResultsFromCoordinates(
                languageCode = languageCode,
                longitude = longitude,
                latitude = latitude,
                restrictions = restrictions
            )) {
            is Result.Success -> {
                val sortedList = sortByDistance(longitude, latitude, response.data)
                Result.Success(sortedList)
            }

            is Result.Error -> response //if needed, map the error to a predefined List of Errors
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
                it.place?.position?.latitude,
                it.place?.position?.longitude
            )
        }

        return result.sortedBy { it.distance }
    }

}