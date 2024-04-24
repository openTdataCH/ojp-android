package ch.opentransportdata.ojp.data.remote

import ch.opentransportdata.ojp.data.dto.OjpDto

/**
 * Created by Michael Ruppen on 08.04.2024
 */
internal interface RemoteOjpDataSource {

    suspend fun searchLocationBySearchTerm(term: String, onlyStation: Boolean): OjpDto
    suspend fun searchLocationByCoordinates(longitude: Double, latitude: Double, onlyStation: Boolean): OjpDto
}