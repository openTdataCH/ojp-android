package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction
import ch.opentransportdata.ojp.domain.model.Result
import ch.opentransportdata.ojp.domain.model.error.OjpError

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal interface RemoteOjpDataSource {

    suspend fun searchLocationBySearchTerm(term: String, restrictions: List<PlaceTypeRestriction>): Result<OjpDto, OjpError>
    suspend fun searchLocationByCoordinates(longitude: Double, latitude: Double, restrictions: List<PlaceTypeRestriction>): Result<OjpDto, OjpError>
}