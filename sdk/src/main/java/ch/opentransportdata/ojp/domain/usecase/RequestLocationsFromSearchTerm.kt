package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.repository.OjpRepository

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RequestLocationsFromSearchTerm(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(
        text: String,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>> {
        return when (val response = ojpRepository.placeResultsFromSearchTerm(term = text, restrictions = restrictions)) {
            is Result.Success -> Result.Success(response.data)
            is Result.Error -> Result.Error(response.error)
        }
    }
}