package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.data.dto.OjpDto
import ch.opentransportdata.ojp.domain.model.PlaceTypeRestriction

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal interface RemoteOjpDataSource {

    suspend fun searchLocationBySearchTerm(term: String, restrictions: List<PlaceTypeRestriction>): OjpDto
    suspend fun searchLocationByCoordinates(longitude: Double, latitude: Double, restrictions: List<PlaceTypeRestriction>): OjpDto
}