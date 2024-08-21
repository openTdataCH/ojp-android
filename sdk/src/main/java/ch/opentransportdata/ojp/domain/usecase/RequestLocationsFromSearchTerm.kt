package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.LanguageCode
import ch.opentransportdata.ojp.domain.model.LocationInformationParams
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.repository.OjpRepository

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RequestLocationsFromSearchTerm(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(
        languageCode: LanguageCode,
        text: String,
        restrictions: LocationInformationParams
    ): Result<List<PlaceResultDto>> {
        return when (val response =
            ojpRepository.placeResultsFromSearchTerm(languageCode = languageCode, term = text, restrictions = restrictions)) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.error)
        }
    }
}