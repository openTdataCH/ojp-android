package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result


internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(
        term: String,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>>

    suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>>
}