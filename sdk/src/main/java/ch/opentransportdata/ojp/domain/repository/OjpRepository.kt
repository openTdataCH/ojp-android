package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Response


internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(term: String, restrictions: List<PlaceTypeRestriction>): Response<List<PlaceResultDto>>
    suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Response<List<PlaceResultDto>>
}