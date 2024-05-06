package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError


internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(
        term: String,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>, OjpError>

    suspend fun placeResultsFromCoordinates(
        longitude: Double,
        latitude: Double,
        restrictions: List<PlaceTypeRestriction>
    ): Result<List<PlaceResultDto>, OjpError>
}