package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.repository.OjpRepository

/**
 * Created by Deniz Kalem on 02.07.2026
 *
 */
internal class RequestLocationsFromRectangle(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(
        languageCode: LanguageCode,
        upperLeftLongitude: Double,
        upperLeftLatitude: Double,
        lowerRightLongitude: Double,
        lowerRightLatitude: Double,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return ojpRepository.placeResultsFromRectangle(
            languageCode = languageCode,
            upperLeftLongitude = upperLeftLongitude,
            upperLeftLatitude = upperLeftLatitude,
            lowerRightLongitude = lowerRightLongitude,
            lowerRightLatitude = lowerRightLatitude,
            restrictions = restrictions
        )
    }
}