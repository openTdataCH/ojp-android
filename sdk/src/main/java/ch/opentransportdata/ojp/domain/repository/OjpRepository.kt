package ch.opentransportdata.ojp.domain.repository

import ch.opentransportdata.ojp.data.dto.response.PlaceResultDto
import ch.opentransportdata.ojp.domain.model.Response


internal interface OjpRepository {
    suspend fun placeResultsFromSearchTerm(term: String, onlyStation: Boolean): Response<List<PlaceResultDto>>
    suspend fun placeResultsFromCoordinates(longitude: Double, latitude: Double, onlyStation: Boolean): Response<List<PlaceResultDto>>
}