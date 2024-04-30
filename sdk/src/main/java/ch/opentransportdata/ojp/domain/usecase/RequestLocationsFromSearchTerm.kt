package ch.opentransportdata.ojp.domain.usecase

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.Response
import ch.opentransportdata.ojp.domain.repository.OjpRepository

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal class RequestLocationsFromSearchTerm(
    private val ojpRepository: OjpRepository
) {

    suspend operator fun invoke(text: String, onlyStation: Boolean): Response<List<PlaceResultDto>> {
        return when (val response = ojpRepository.placeResultsFromSearchTerm(term = text, onlyStation)) {
            is Response.Success -> response
            is Response.Error -> response //if needed, map the error to a predefined List of Errors
        }
    }
}